package com.jbucloud.festival.controller;

import com.jbucloud.festival.domain.GameType;
import com.jbucloud.festival.domain.Question;
import com.jbucloud.festival.repository.QuestionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final QuestionRepository questionRepository;

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
