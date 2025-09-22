package com.jbucloud.festival.global.response.status;

import com.jbucloud.festival.global.response.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseErrorCode {
    OK(HttpStatus.OK, "COMMON200", "요청이 성공적으로 처리되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override public boolean isSuccess() { return true; }
}
