package org.springsec.springsecurityjwt.request;

import lombok.*;

@Getter
@Setter @AllArgsConstructor @NoArgsConstructor
@Builder
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String confirmPassword;





}
