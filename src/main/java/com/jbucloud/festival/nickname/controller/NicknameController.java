package com.jbucloud.festival.nickname.controller;

import com.jbucloud.festival.global.response.ApiResponse;
import com.jbucloud.festival.nickname.dto.NicknameResponse;
import com.jbucloud.festival.nickname.service.NicknameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Nickname", description = "닉네임 생성 관련 API")
@RestController
@RequestMapping("/api/v1/nickname")
@RequiredArgsConstructor
public class NicknameController {

    private final NicknameService nicknameService;

    @Operation(summary = "임시 닉네임 생성", description = "채팅방에서 사용할 임시 닉네임을 생성합니다.")
    @GetMapping
    public ApiResponse<NicknameResponse> generateNickname() {
        String nickname = nicknameService.generateNickname();
        return ApiResponse.onSuccess(new NicknameResponse(nickname));
    }
}
