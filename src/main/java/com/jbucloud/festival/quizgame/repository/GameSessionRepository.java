package com.jbucloud.festival.quizgame.repository;

import com.jbucloud.festival.quizgame.domain.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    Optional<GameSession> findBySubmissionId(String submissionId);
}
