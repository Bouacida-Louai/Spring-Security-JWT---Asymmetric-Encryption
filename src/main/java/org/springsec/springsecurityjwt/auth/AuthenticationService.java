package org.springsec.springsecurityjwt.auth;

import org.springframework.stereotype.Component;
import org.springsec.springsecurityjwt.request.AuthenticationRequest;
import org.springsec.springsecurityjwt.request.RefreshRequest;
import org.springsec.springsecurityjwt.request.RegistrationRequest;
import org.springsec.springsecurityjwt.response.AuthenticationResponse;

@Component
public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register( RegistrationRequest  request);

    AuthenticationResponse refreshToken(RefreshRequest request);





}
