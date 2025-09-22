package com.jbucloud.festival.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class GameSession {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String submissionId;

    @Column(nullable = false)
    private String submissionToken;

    private GameType gameType;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @ManyToMany(fetch = FetchType.EAGER) // 세션 조회 시 문제 바로 로딩
    @JoinTable(name = "session_questions",
            joinColumns = @JoinColumn(name = "game_session_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    @OrderColumn(name = "question_order") // 문제 순서 보장
    private List<Question> questions = new ArrayList<>();


    private Integer score;
    private ZonedDateTime finishedAt;

    public GameSession(String submissionId, String submissionToken, GameType gameType, List<Question> questions) {
        this.submissionId = submissionId;
        this.submissionToken = submissionToken;
        this.gameType = gameType;
        this.questions = questions;
        this.status = SessionStatus.IN_PROGRESS;
    }

    public void finishSession(int score) {
        this.status = SessionStatus.FINISHED;
        this.score = score;
        this.finishedAt = ZonedDateTime.now();
    }

    public enum SessionStatus {
        IN_PROGRESS, FINISHED, RECORDED
    }
}
