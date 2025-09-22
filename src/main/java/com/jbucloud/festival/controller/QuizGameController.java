package com.jbucloud.festival.controller;


import com.jbucloud.festival.dto.GameDto;
import com.jbucloud.festival.service.QuizGameService;
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
    public ResponseEntity<GameDto.StartResponse> startGame(@RequestBody GameDto.StartRequest request) {
        GameDto.StartResponse response = quizGameService.startGame(request.gameType());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/submissions/{id}/questions")
    public ResponseEntity<GameDto.QuestionResponse> getQuestions(
            @PathVariable("id") String submissionId,
            @RequestHeader("X-Submission-Token") String submissionToken,
            @RequestParam(value = "cursor", defaultValue = "0") int cursor,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        GameDto.QuestionResponse response = quizGameService.getQuestions(submissionId, submissionToken, cursor, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submissions/{id}/finish")
    public ResponseEntity<GameDto.FinishResponse> finishSubmission(
            @PathVariable("id") String submissionId,
            @RequestHeader("X-Submission-Token") String submissionToken,
            @RequestBody GameDto.AnswerRequest request
    ) {
        GameDto.FinishResponse response = quizGameService.finishSubmission(submissionId, submissionToken,request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/scores")
    public ResponseEntity<GameDto.ScoreRegisterResponse> registerScore(
            @RequestBody GameDto.ScoreRegisterRequest request,
            @RequestHeader("X-Submission-Token") String submissionToken,
            @AuthenticationPrincipal Long memberId
    ) {
        quizGameService.registerScore(memberId, submissionToken, request.submissionId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GameDto.ScoreRegisterResponse(true, "점수가 성공적으로 등록되었습니다."));
    }
}
