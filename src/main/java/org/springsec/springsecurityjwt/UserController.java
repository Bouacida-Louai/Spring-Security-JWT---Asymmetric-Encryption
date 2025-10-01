package org.springsec.springsecurityjwt;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springsec.springsecurityjwt.service.UserService;
import org.springsec.springsecurityjwt.user.User;
import org.springsec.springsecurityjwt.user.request.ChangePasswordRequest;
import org.springsec.springsecurityjwt.user.request.ProfileUpdateRequest;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Getter
@Setter
@Tag(name = "User" ,description = "User API")
public class UserController {
    private final UserService userService;


    @PatchMapping("/me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateProfile(
            @RequestBody @Valid final ProfileUpdateRequest  request
            ,final String userId
            ,final Authentication principal
            ) {
        this.userService.updateProfileInf(request,getUserId(principal));
    }

    @PostMapping("/me/password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updatePassword(
            @RequestBody @Valid final ChangePasswordRequest request ,
            final Authentication principal) {
        this.userService.changePassword(request,getUserId(principal));
    }

    @PatchMapping("/me/deactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deactivateAccount(Authentication principal) {
        this.userService.deActivateAccount(getUserId(principal));
    }

    @PatchMapping("/me/activate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void reactivateAccount(Authentication principal) {
        this.userService.reActivateAccount(getUserId(principal));
    }

    @DeleteMapping("/me/delete")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAccount(Authentication principal) {
        this.userService.deleteAccount(getUserId(principal));
    }










    private String getUserId(Authentication principal) {
        return ((User) principal.getPrincipal()).getId();
    }


}
