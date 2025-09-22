package com.jbucloud.festival.domain;

// Question.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameType gameType;
    private String stem;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
    private List<Choice> choices = new ArrayList<>();

    private String correctAnswer; // 정답
    
    public void update(GameType gameType, String stem, List<Choice> choices, String correctAnswer) {
        this.gameType = gameType;
        this.stem = stem;
        this.choices.clear();
        this.choices.addAll(choices);
        this.correctAnswer = correctAnswer;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Choice {
        private String choiceId;
        private String label;
    }
}