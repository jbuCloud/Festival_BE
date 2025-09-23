package com.jbucloud.festival.global.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.kakao")
@Getter
@Setter
public class KakaoProviderProperties {
    private String tokenUri;
    private String userInfoUri;
}
