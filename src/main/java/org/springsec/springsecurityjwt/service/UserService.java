package org.springsec.springsecurityjwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springsec.springsecurityjwt.user.request.ChangePasswordRequest;
import org.springsec.springsecurityjwt.user.request.ProfileUpdateRequest;

public interface UserService extends UserDetailsService {

    void updateProfileInf(ProfileUpdateRequest request,String userId);

    void changePassword(ChangePasswordRequest request, String userId);

    void deActivateAccount(String userId);

    void reActivateAccount(String userId);

    void deleteAccount(String userId);


}
