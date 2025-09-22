package com.jbucloud.festival.domain.member.controller;

import com.jbucloud.festival.domain.auth.dto.TokenDto;
import com.jbucloud.festival.domain.member.dto.LoginRequestDto;
import com.jbucloud.festival.domain.member.dto.SignUpRequestDto;
import com.jbucloud.festival.domain.member.dto.UpdateMajorRequestDto;
import com.jbucloud.festival.domain.member.service.MemberService;
import com.jbucloud.festival.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "로컬 회원가입 및 로그인 관련 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로컬 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignUpRequestDto requestDto) {
        memberService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @Operation(summary = "로컬 로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequestDto requestDto) {
        TokenDto tokenDto = memberService.login(requestDto);
        return ResponseEntity.ok(tokenDto);
    }

    @Operation(summary = "회원 전공 업데이트", description = "소셜 로그인 후 전공 정보를 추가하거나 업데이트합니다.")
    @PatchMapping("/major")
    public ResponseEntity<String> updateMajor(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid UpdateMajorRequestDto requestDto) {
        memberService.updateMajor(userDetails.getMember().getId(), requestDto.getMajor());
        return ResponseEntity.ok("전공 정보가 성공적으로 업데이트되었습니다.");
    }
}
