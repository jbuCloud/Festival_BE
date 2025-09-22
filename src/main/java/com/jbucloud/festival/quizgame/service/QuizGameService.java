package com.jbucloud.festival.quizgame.service;


import com.jbucloud.festival.global.exception.GeneralException;
import com.jbucloud.festival.global.response.status.ErrorStatus;
import com.jbucloud.festival.quizgame.domain.GameSession;
import com.jbucloud.festival.quizgame.domain.GameType;
import com.jbucloud.festival.quizgame.domain.Question;
import com.jbucloud.festival.quizgame.dto.GameDto;
import com.jbucloud.festival.quizgame.repository.GameSessionRepository;
import com.jbucloud.festival.quizgame.repository.QuestionRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizGameService {

    private final GameSessionRepository gameSessionRepository;
    private final QuestionRepository questionRepository;

    // private final ScoreRepository scoreRepository; // 점수 등록


    @Transactional
    public GameDto.StartResponse startGame(GameType gameType) {
        List<Question> questionsForGame = new ArrayList<>(questionRepository.findRandomQuestions(gameType.name(),30)); //30개 추출
        if (questionsForGame.isEmpty()) {
            throw new GeneralException(ErrorStatus.QUIZ_NOT_FOUND);
        }

        String submissionId = "s-" + UUID.randomUUID().toString().substring(0, 8);
        String submissionToken = "st-" + UUID.randomUUID();

        GameSession newSession = new GameSession(submissionId, submissionToken, gameType, questionsForGame);
        gameSessionRepository.save(newSession);

        return new GameDto.StartResponse(submissionId, submissionToken);
    }

    @Transactional
    public GameDto.QuestionResponse getQuestions(String submissionId, String token, int cursor, int size) {
        GameSession session = validateAndGetSession(submissionId, token);

        List<Question> allQuestions = session.getQuestions();
        if (cursor >= allQuestions.size()) {
            return new GameDto.QuestionResponse(Collections.emptyList(), null);
        }

        int end = Math.min(cursor + size, allQuestions.size());
        List<Question> paginatedQuestions = allQuestions.subList(cursor, end);

        List<GameDto.Question> questionDtos = paginatedQuestions.stream()
                .map(q -> new GameDto.Question(
                        q.getStem(),
                        q.getChoices().stream()
                                .map(c -> new GameDto.Choice(c.getChoiceId(), c.getLabel()))
                                .collect(Collectors.toList())
                )).toList();

        Integer nextCursor = (end < allQuestions.size()) ? end : null;
        return new GameDto.QuestionResponse(questionDtos, nextCursor);
    }



    @Transactional
    public GameDto.FinishResponse finishSubmission(String submissionId, String token, GameDto.AnswerRequest request) {
        GameSession session = validateAndGetSession(submissionId, token);

        if (session.getStatus() == GameSession.SessionStatus.FINISHED) {
            throw new GeneralException(ErrorStatus.QUIZ_ALREADY_CLOSED);
        }

        int scorePerQuestion = 10;
        int totalScore = 0;
        int maxScore = session.getQuestions().size() * scorePerQuestion;

        List<Question> questions = session.getQuestions();
        List<String> userAnswers = request.answers();

        for (int i = 0; i < userAnswers.size(); i++) {
            Question question = questions.get(i);

            String correctAnswer = question.getCorrectAnswer();
            log.info("정답:{}",correctAnswer);
            log.info("선택:{}",userAnswers.get(i));
            if (correctAnswer != null && correctAnswer.equals(userAnswers.get(i))) {
                totalScore += scorePerQuestion;
            }
        }

        session.finishSession(totalScore);
        gameSessionRepository.save(session);

        return new GameDto.FinishResponse("finished", totalScore, maxScore, session.getFinishedAt());
    }


    public void registerScore(Long memberId, String submissionToken, @NotEmpty String submissionId) {
        //점수등록
    }

    private GameSession validateAndGetSession(String submissionId, String token) {
        GameSession session = gameSessionRepository.findBySubmissionId(submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found with id: " + submissionId));


        if (!session.getSubmissionToken().equals(token)) {
            throw new SecurityException("Invalid submission token.");
        }
        return session;
    }
}