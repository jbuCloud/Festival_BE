package com.jbucloud.festival.domain.auth.controller;

import com.jbucloud.festival.domain.auth.dto.TokenDto;
import com.jbucloud.festival.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "인증", description = "로그인 및 인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "카카오 로그인", description = "카카오 인가 코드를 사용하여 로그인하고 JWT를 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenDto.class)) }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 인가 코드)",
                    content = @Content)
    })
    @GetMapping("/oauth/kakao")
    public Mono<ResponseEntity<TokenDto>> kakaoLogin(@Parameter(description = "카카오 서버에서 발급받은 인가 코드", required = true) @RequestParam("code") String code) {
        return authService.loginWithKakao(code)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
