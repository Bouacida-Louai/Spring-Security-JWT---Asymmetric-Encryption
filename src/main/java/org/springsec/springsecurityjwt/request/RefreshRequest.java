package org.springsec.springsecurityjwt.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RefreshRequest {

    private String refreshToken;

}
