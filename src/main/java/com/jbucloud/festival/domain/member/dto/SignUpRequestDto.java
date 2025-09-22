package com.jbucloud.festival.domain.member.dto;

import com.jbucloud.festival.global.validation.ValidMajor;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String nickname;
    @NotBlank(message = "전공은 필수 입력 항목입니다.")
    @ValidMajor
    private String major;
    private String email;
    private String password;
}
