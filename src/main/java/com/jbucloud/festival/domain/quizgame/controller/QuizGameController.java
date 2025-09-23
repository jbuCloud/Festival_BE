package com.jbucloud.festival.domain.quizgame.controller;

import com.jbucloud.festival.global.response.ApiResponse;
import com.jbucloud.festival.domain.quizgame.dto.GameDto;
import com.jbucloud.festival.domain.quizgame.service.QuizGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "퀴즈 게임", description = "퀴즈 게임 관련 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuizGameController {

    private final QuizGameService quizGameService;

    @Operation(
            summary = "게임 시작",
            description = "새로운 퀴즈 게임을 시작합니다. 게임 타입을 지정하여 해당 유형의 퀴즈로 게임을 생성합니다."
    )
    @PostMapping("/games/start")
    public ResponseEntity<ApiResponse<GameDto.StartResponse>> startGame(
            @RequestBody GameDto.StartRequest request
    ) {
        GameDto.StartResponse response = quizGameService.startGame(request.gameType());
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @Operation(
            summary = "퀴즈 문제 조회",
            description = "게임 세션의 퀴즈 문제들을 페이징하여 조회합니다. cursor와 size를 이용한 페이징을 지원합니다."
    )
    @GetMapping("/submissions/{id}/questions")
    public ResponseEntity<ApiResponse<GameDto.QuestionResponse>> getQuestions(
            @Parameter(description = "제출 세션 ID", example = "sub_20240101_abcd1234")
            @PathVariable("id") String submissionId,
            
            @Parameter(description = "제출 토큰", example = "token_xyz789mnop456qrs")
            @RequestHeader("X-Submission-Token") String submissionToken,
            
            @Parameter(description = "페이징 커서 (시작 위치)", example = "0")
            @RequestParam(value = "cursor", defaultValue = "0") int cursor,
            
            @Parameter(description = "페이지 크기", example = "5")
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        GameDto.QuestionResponse response = quizGameService.getQuestions(submissionId, submissionToken, cursor, size);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @Operation(
            summary = "게임 완료",
            description = "퀴즈 게임을 완료하고 답안을 제출합니다. 제출된 답안을 바탕으로 점수가 계산됩니다."
    )
    @PostMapping("/submissions/{id}/finish")
    public ResponseEntity<ApiResponse<GameDto.FinishResponse>> finishSubmission(
            @Parameter(description = "제출 세션 ID", example = "s-32b3696b")
            @PathVariable("id") String submissionId,
            
            @Parameter(description = "제출 토큰", example = "st-682508ac-cb51-45bc-aaa3-308ed538f59e")
            @RequestHeader("X-Submission-Token") String submissionToken,
            
            @RequestBody GameDto.AnswerRequest request
    ) {
        GameDto.FinishResponse response = quizGameService.finishSubmission(submissionId, submissionToken, request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @Operation(
            summary = "점수 등록",
            description = "완료된 게임의 점수를 사용자 계정에 등록합니다. 인증된 사용자만 점수를 등록할 수 있습니다."
    )
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/submissions/{id}/score")
    public ResponseEntity<ApiResponse<Void>> registerScore(
            @Parameter(description = "제출 세션 ID", example = "s-32b3696b")
            @PathVariable("id") String submissionId,

            @Parameter(description = "제출 토큰", example = "st-682508ac-cb51-45bc-aaa3-308ed538f59e")
            @RequestHeader("X-Submission-Token") String submissionToken,
            
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId
    ) {
        quizGameService.registerScore(memberId, submissionToken, submissionId);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
