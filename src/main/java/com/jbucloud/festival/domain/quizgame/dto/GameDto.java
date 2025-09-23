package com.jbucloud.festival.domain.quizgame.dto;

import com.jbucloud.festival.domain.quizgame.domain.GameType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

public class GameDto {

    // --- Request ---

    @Schema(description = "게임 시작 요청")
    public record StartRequest(
            @Schema(description = "게임 타입", example = "DADJOKE", allowableValues = {"DADJOKE", "CAMPUS", "TTS"})
            @NotEmpty GameType gameType
    ) {}

    @Schema(description = "답안 제출 요청")
    public record AnswerRequest(
            @Schema(description = "답안 목록 (순서대로 제출)", example = "[\"3\", \"1\", \"서울\", \"4\", \"2\"]")
            List<String> answers
    ) {}

    @Schema(description = "점수 등록 요청")
    public record ScoreRegisterRequest(
            @Schema(description = "제출 세션 ID", example = "s-32b3696b")
            @NotEmpty String submissionId
    ) {}

    // --- Response  ---

    @Schema(description = "게임 시작 응답")
    public record StartResponse(
            @Schema(description = "제출 세션 ID", example = "s-32b3696b")
            String submissionId,
            
            @Schema(description = "제출 토큰", example = "st-682508ac-cb51-45bc-aaa3-308ed538f59e")
            String submissionToken
    ) {}

    @Schema(description = "퀴즈 문제 응답")
    public record QuestionResponse(
            @Schema(description = "퀴즈 문제 목록")
            List<Question> questions,
            
            @Schema(description = "다음 페이지 커서 (null이면 마지막 페이지)", example = "5")
            Integer nextCursor
    ) {}

    @Schema(name = "GameQuestion", description = "퀴즈 문제")
    public record Question(
            @Schema(description = "문제 내용", example = "대한민국의 수도는?")
            String stem,
            
            @Schema(description = "선택지 목록 (주관식인 경우 빈 배열)")
            List<Choice> choices
    ) {}

    @Schema(name = "GameChoice", description = "선택지")
    public record Choice(
            @Schema(description = "선택지 ID", example = "1")
            String choiceId,

            @Schema(description = "선택지 내용", example = "서울")
            String label
    ) {
    }


    @Schema(description = "게임 완료 응답")
    public record FinishResponse(
            @Schema(description = "게임 상태", example = "IN_PROGRESS", allowableValues = {"IN_PROGRESS", "FINISHED", "RECORDED"})
            String status,
            
            @Schema(description = "획득한 점수", example = "30")
            int scoreTotal,
            
            @Schema(description = "최대 점수", example = "100")
            int scoreMax,
            
            @Schema(description = "완료 시간", example = "2024-01-15T14:30:00+09:00")
            ZonedDateTime finishedAt
    ) {}
}
