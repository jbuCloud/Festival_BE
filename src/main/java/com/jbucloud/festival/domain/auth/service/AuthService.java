package com.jbucloud.festival.domain.auth.service;

import com.jbucloud.festival.domain.auth.dto.TokenDto;
import com.jbucloud.festival.domain.auth.dto.kakao.KakaoTokenResponse;
import com.jbucloud.festival.domain.auth.dto.kakao.KakaoUserInfoResponse;
import com.jbucloud.festival.domain.member.entity.Member;
import com.jbucloud.festival.domain.member.repository.MemberRepository;
import com.jbucloud.festival.global.jwt.JwtUtil;
import com.jbucloud.festival.global.oauth.properties.KakaoProviderProperties;
import com.jbucloud.festival.global.oauth.properties.KakaoRegistrationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final KakaoRegistrationProperties registrationProperties;
    private final KakaoProviderProperties providerProperties;
    private final WebClient webClient = WebClient.create();

    @Transactional
    public Mono<TokenDto> loginWithKakao(String authCode) {
        return getKakaoToken(authCode)
                .flatMap(tokenResponse -> getKakaoUserInfo(tokenResponse.getAccessToken()))
                .flatMap(this::saveOrUpdateMember)
                .map(member -> jwtUtil.generateTokens(member.getId()));
    }

    private Mono<KakaoTokenResponse> getKakaoToken(String authCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", registrationProperties.getAuthorizationGrantType());
        formData.add("client_id", registrationProperties.getClientId());
        formData.add("redirect_uri", registrationProperties.getRedirectUri());
        formData.add("code", authCode);

        return webClient.post()
                .uri(providerProperties.getTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class);
    }

    private Mono<KakaoUserInfoResponse> getKakaoUserInfo(String accessToken) {
        return webClient.get()
                .uri(providerProperties.getUserInfoUri())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfoResponse.class);
    }

    private Mono<Member> saveOrUpdateMember(KakaoUserInfoResponse userInfo) {
        return Mono.fromCallable(() -> {
            Member member = memberRepository.findByProviderAndProviderId("kakao", userInfo.getId().toString())
                    .orElseGet(() -> Member.builder()
                            .email(userInfo.getKakaoAccount().getEmail())
                            .nickname(userInfo.getKakaoAccount().getProfile().getNickname())
                            .provider("kakao")
                            .providerId(userInfo.getId().toString())
                            .build());
            return memberRepository.save(member);
        });
    }
}
