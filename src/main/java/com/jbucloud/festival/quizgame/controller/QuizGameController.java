package com.jbucloud.festival.quizgame.controller;


import com.jbucloud.festival.global.response.ApiResponse;
import com.jbucloud.festival.quizgame.dto.GameDto;
import com.jbucloud.festival.quizgame.service.QuizGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuizGameController {

    private final QuizGameService quizGameService;

    @PostMapping("/games/start")
    public ResponseEntity<ApiResponse<GameDto.StartResponse>> startGame(@RequestBody GameDto.StartRequest request) {
        GameDto.StartResponse response = quizGameService.startGame(request.gameType());
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @GetMapping("/submissions/{id}/questions")
    public ResponseEntity<ApiResponse<GameDto.QuestionResponse>> getQuestions(
            @PathVariable("id") String submissionId,
            @RequestHeader("X-Submission-Token") String submissionToken,
            @RequestParam(value = "cursor", defaultValue = "0") int cursor,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        GameDto.QuestionResponse response = quizGameService.getQuestions(submissionId, submissionToken, cursor, size);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @PostMapping("/submissions/{id}/finish")
    public ResponseEntity<ApiResponse<GameDto.FinishResponse>> finishSubmission(
            @PathVariable("id") String submissionId,
            @RequestHeader("X-Submission-Token") String submissionToken,
            @RequestBody GameDto.AnswerRequest request
    ) {
        GameDto.FinishResponse response = quizGameService.finishSubmission(submissionId, submissionToken,request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @PostMapping("/scores")
    public ResponseEntity<ApiResponse<Void>> registerScore(
            @RequestBody GameDto.ScoreRegisterRequest request,
            @RequestHeader("X-Submission-Token") String submissionToken,
            @AuthenticationPrincipal Long memberId
    ) {
        quizGameService.registerScore(memberId, submissionToken, request.submissionId());
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
