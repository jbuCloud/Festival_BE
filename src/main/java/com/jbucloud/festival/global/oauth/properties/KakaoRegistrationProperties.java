package com.jbucloud.festival.global.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.kakao")
@Getter
@Setter
public class KakaoRegistrationProperties {
    private String clientId;
    private String redirectUri;
    private String clientAuthenticationMethod;
    private String authorizationGrantType;
    private String scope;
}
