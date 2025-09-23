package com.jbucloud.festival.domain.quizgame.controller;

import com.jbucloud.festival.domain.auth.dto.TokenDto;
import com.jbucloud.festival.domain.member.entity.Member;
import com.jbucloud.festival.domain.member.repository.MemberRepository;
import com.jbucloud.festival.domain.quizgame.domain.GameType;
import com.jbucloud.festival.domain.quizgame.domain.Question;
import com.jbucloud.festival.domain.quizgame.dto.TestDto;
import com.jbucloud.festival.domain.quizgame.repository.QuestionRepository;
import com.jbucloud.festival.global.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "퀴즈 데이터 관리", description = "운영에서는 비활성화")
@Profile("dev")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Value("${firebase.service-account-file}")
    private String serviceAccountFile;

    private final MemberRepository memberRepository;

    private final ResourceLoader resourceLoader;

    private final QuestionRepository questionRepository;

    private final JwtUtil jwtUtil;

    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/api/questions")
    @ResponseBody
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/api/questions/{gameType}")
    @ResponseBody
    public ResponseEntity<List<Question>> getQuestionsByGameType(@PathVariable GameType gameType) {
        List<Question> questions = questionRepository.findByGameType(gameType);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/api/questions")
    @ResponseBody
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionCreateRequest request) {
        Question question = Question.builder()
                .gameType(request.getGameType())
                .stem(request.getStem())
                .choices(request.getChoices())
                .correctAnswer(request.getCorrectAnswer())
                .build();

        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.ok(savedQuestion);
    }

    @PutMapping("/api/questions/{id}")
    @ResponseBody
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody QuestionCreateRequest request) {
        return questionRepository.findById(id)
                .map(question -> {
                    question.update(request.getGameType(), request.getStem(), request.getChoices(), request.getCorrectAnswer());
                    Question saved = questionRepository.save(question);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 퀴즈 삭제
    @DeleteMapping("/api/questions/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/test")
    @ResponseBody
    public void testLogger() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + serviceAccountFile);
        String jsonContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        log.info("Resource exists {}", jsonContent);
    }

    @GetMapping("/auth")
    @ResponseBody
    public ResponseEntity<TokenDto> getAuth(@RequestParam(value = "memberId", defaultValue = "1") Long memberId) {
        TokenDto tokenDto = jwtUtil.generateTokens(memberId);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/tester")
    @ResponseBody
    public Long createTester(@RequestBody TestDto.CreateMemberRequest request) {
        Member member = Member.builder()
                .nickname(request.nickname())
                .major(request.major())
                .build();
        return memberRepository.save(member).getId();
    }

    @GetMapping("/ping")
    @ResponseBody
    public String pingpong() {
        return "Pong";
    }


    // 요청 DTO
    @Getter
    @Setter
    public static class QuestionCreateRequest {
        private GameType gameType;
        private String stem;
        private List<Question.Choice> choices;
        private String correctAnswer;
    }
}
