package com.jbucloud.festival.quizgame.dto;


import com.jbucloud.festival.quizgame.domain.GameType;
import jakarta.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

public class GameDto {

    // --- Request ---

    public record StartRequest(
            @NotEmpty GameType gameType
    ) {}

    public record AnswerRequest(
            List<String> answers
    ) {}


    public record ScoreRegisterRequest(
            @NotEmpty String submissionId
    ) {}


    // --- Response  ---

    public record StartResponse(
            String submissionId,
            String submissionToken
    ) {}

    public record QuestionResponse(
            List<Question> questions,
            Integer nextCursor
    ) {}

    public record Question(
            String stem,
            List<Choice> choices
    ) {}

    public record Choice(
            String choiceId,
            String label
    ) {}

    public record SaveResponse(
            boolean saved
    ) {}

    public record FinishResponse(
            String status,
            int scoreTotal,
            int scoreMax,
            ZonedDateTime finishedAt
    ) {}

    public record ScoreRegisterResponse(
            boolean success,
            String message
    ) {}
}
