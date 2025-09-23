package com.jbucloud.festival.domain.quizgame.dto;

import com.jbucloud.festival.domain.quizgame.domain.GameType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class TestDto {
    @Schema(description = "테스트용 회원 생성")
    public record CreateMemberRequest(
            @Schema(description = "닉네임", example = "홍길동")
            @NotEmpty String nickname,

            @Schema(description = "전공", example = "인공지능전공", allowableValues = {"만화애니메이션학전공", "정보보호학전공", "자율설계전공학부"})
            @NotEmpty String major
    ) {}

    @Schema(description = "답안 제출 요청")
    public record AnswerRequest(
            @Schema(description = "답안 목록 (순서대로 제출)", example = "[\"3\", \"1\", \"서울\", \"4\", \"2\"]")
            List<String> answers
    ) {}
}
