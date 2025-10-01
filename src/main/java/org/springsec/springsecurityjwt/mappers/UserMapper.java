package org.springsec.springsecurityjwt.mappers;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springsec.springsecurityjwt.request.RegistrationRequest;
import org.springsec.springsecurityjwt.user.User;
import org.springsec.springsecurityjwt.user.request.ProfileUpdateRequest;

import static jdk.internal.classfile.impl.DirectCodeBuilder.build;

@Service
public class UserMapper {

    public void mergeUserinfo(User user, ProfileUpdateRequest request) {

        if (StringUtils.isNotBlank(request.getFirstName())
        &&!user.getFirstName().equals(request.getFirstName()))
        {
            user.setFirstName(request.getFirstName());
        }

        if (StringUtils.isNotBlank(request.getLastName())
                &&!user.getLastName().equals(request.getLastName()))
        {
            user.setLastName(request.getLastName());
        }

        if (request.getDateOfBirth() !=null
        && !request.getDateOfBirth().equals(user.getDateOfBirth())){
            user.setDateOfBirth(request.getDateOfBirth());
        }

    }

    public User toUser(RegistrationRequest request) {
        User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .phoneNumber(request.getPhoneNumber())
            .password(request.getPassword())
            .enabled(true)
            .locked(false)
            .emailVerified(false)
             .phoneVerified(false)
             .credentialsExpired(false)
                     .
                            build();

    }
}
