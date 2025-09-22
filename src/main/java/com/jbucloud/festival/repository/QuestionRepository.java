package com.jbucloud.festival.repository;

import com.jbucloud.festival.domain.GameType;
import com.jbucloud.festival.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT * FROM question WHERE game_type = :gameType ORDER BY RAND() LIMIT :count",
            nativeQuery = true)
    List<Question> findRandomQuestions(@Param("gameType") String gameType, @Param("count") int count);
    
    List<Question> findByGameType(GameType gameType);
}
