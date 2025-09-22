package com.jbucloud.festival.global.response.status;

import com.jbucloud.festival.global.response.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),


    //퀴즈관련 예외
    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "QUIZ404", "문제가 비었습니다."),
    QUIZ_ALREADY_CLOSED(HttpStatus.FORBIDDEN, "QUIZ403", "이미 종료된 게임 세션입니다.");





    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public boolean isSuccess() {
        return false;
    }
}
