package org.springsec.springsecurityjwt.auth;

import org.springsec.springsecurityjwt.request.AuthenticationRequest;
import org.springsec.springsecurityjwt.request.RefreshRequest;
import org.springsec.springsecurityjwt.request.RegistrationRequest;
import org.springsec.springsecurityjwt.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register( RegistrationRequest  request);

    AuthenticationResponse refreshToken(RefreshRequest request);





}
