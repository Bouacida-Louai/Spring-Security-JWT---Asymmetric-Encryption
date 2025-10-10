package org.springsec.springsecurityjwt.impl;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springsec.springsecurityjwt.auth.AuthenticationService;
import org.springsec.springsecurityjwt.exceptions.BuisnessException;
import org.springsec.springsecurityjwt.exceptions.ErrorCode;
import org.springsec.springsecurityjwt.mappers.UserMapper;
import org.springsec.springsecurityjwt.repository.RoleRepository;
import org.springsec.springsecurityjwt.repository.UserRepository;
import org.springsec.springsecurityjwt.request.AuthenticationRequest;
import org.springsec.springsecurityjwt.request.RefreshRequest;
import org.springsec.springsecurityjwt.request.RegistrationRequest;
import org.springsec.springsecurityjwt.response.AuthenticationResponse;
import org.springsec.springsecurityjwt.role.Role;
import org.springsec.springsecurityjwt.security.JwtService;
import org.springsec.springsecurityjwt.user.User;

import java.util.ArrayList;
import java.util.List;


@Server
@RequiredArgsConstructor
@Slf4j

public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager  authenticationManager;
    private final JwtService  jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper  userMapper;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final Authentication auth =this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail()
                        ,request.getPassword()
                )
        );
        final User user =(User) auth.getPrincipal();
        final String token=this.jwtService.generateAccessToken(user.getUsername());
        final String  refreshToken=this.jwtService.generateRefreshToken(user.getUsername());
        final String tokenType="Bearer";
        return   AuthenticationResponse.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .tokenType(tokenType)

                        .build();
    }



    @Override
    @Transactional
    public void register(RegistrationRequest request) {

        chekUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkPassword(request.getPassword(),request.getConfirmPassword());

       final Role userRole = this.roleRepository.findByName("ROLE_USER")
               .orElseThrow(()->new EntityNotFoundException("Role User not found"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user =this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving user {}",user);
        this.userRepository.save(user);

        final List<User> users =new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);
        this.roleRepository.save(userRole);

    }

    private void chekUserEmail(String email) {

        final boolean emailExists = this.userRepository.existsByEmailIgnoreCase(email);
        if (emailExists){
            throw new BuisnessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = this.userRepository.existsByPhoneNumberIgnoreCase(phoneNumber);
        if (phoneNumberExists){

            throw new BuisnessException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);

        }

    }

    private void checkPassword(String password , String confirmPassword) {
        if (password ==null || !password.equals(confirmPassword)){
            throw new BuisnessException(ErrorCode.PASSWORD_MISMATCH);
        }


    }



    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken=jwtService.generateRefreshToken(request.getRefreshToken());
        final String tokenType="Bearer";
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();


    }
}
