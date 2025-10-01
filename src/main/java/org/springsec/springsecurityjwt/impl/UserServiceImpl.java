package org.springsec.springsecurityjwt.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springsec.springsecurityjwt.exceptions.BuisnessException;
import org.springsec.springsecurityjwt.exceptions.ErrorCode;
import org.springsec.springsecurityjwt.mappers.UserMapper;
import org.springsec.springsecurityjwt.repository.UserRepository;
import org.springsec.springsecurityjwt.service.UserService;
import org.springsec.springsecurityjwt.user.User;
import org.springsec.springsecurityjwt.user.request.ChangePasswordRequest;
import org.springsec.springsecurityjwt.user.request.ProfileUpdateRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    BuisnessException buisnessException;



     private  final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final UserDetailsService userDetailsService;
     private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return this.userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()
                        -> new UsernameNotFoundException("User not found with username : " + userEmail));
    }

    @Override
    public void updateProfileInf(ProfileUpdateRequest request, String userId)  {
        final User savedUser = userRepository.findById(userId).orElseThrow(()
                -> new BuisnessException(ErrorCode.USER_NOT_FOUND,userId)  );
        this.userMapper.mergeUserinfo(savedUser,request);
        userRepository.save(savedUser);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String userId) {

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new BuisnessException(ErrorCode.CHANGE_PASSWORD_MISMATCH);
        }
        final User savedUser = userRepository.findById(userId)
                .orElseThrow(()-> new BuisnessException(ErrorCode.USER_NOT_FOUND,userId)  );

        if (!this.passwordEncoder.matches(request.getCurrentPassword(),savedUser.getPassword())){
            throw new BuisnessException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }
            final String encoded = this.passwordEncoder.encode(request.getNewPassword());
        savedUser.setPassword(encoded);
        this.userRepository.save(savedUser);
    }

    @Override
    public void deActivateAccount(String userId) {
        final User user =userRepository.findById(userId)
                .orElseThrow(()-> new BuisnessException(ErrorCode.USER_NOT_FOUND,userId)  );
        if (!user.isEnabled()){
            throw new BuisnessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void reActivateAccount(String userId) {

        final User user =userRepository.findById(userId)
                .orElseThrow(()-> new BuisnessException(ErrorCode.USER_NOT_FOUND,userId)  );
        if (user.isEnabled()){
            throw new BuisnessException(ErrorCode.ACCOUNT_ALREADY_ACTIVATED);
        }
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void deleteAccount(String userId) {
        // this methode need the rest of the entities
        //

    }


}
