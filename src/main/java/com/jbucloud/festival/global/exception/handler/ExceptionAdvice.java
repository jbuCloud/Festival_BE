package com.jbucloud.festival.global.exception.handler;

import com.jbucloud.festival.global.exception.GeneralException;
import com.jbucloud.festival.global.response.ApiResponse;
import com.jbucloud.festival.global.response.BaseErrorCode;
import com.jbucloud.festival.global.response.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    // 사용자 정의 예외
    @ExceptionHandler(GeneralException.class)
    protected ResponseEntity<ApiResponse<Void>> handleGeneral(GeneralException ex) {
        BaseErrorCode ec = ex.getCode();
        return ResponseEntity
                .status(ec.getHttpStatus())
                .body(ApiResponse.of(ec, null));
    }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleAll(Exception ex, HttpServletRequest req) {
        log.info(ex.getMessage());
        BaseErrorCode ec = ErrorStatus.BAD_REQUEST;
        return ResponseEntity
                .status(ec.getHttpStatus())
                .body(ApiResponse.of(ec, null));
    }
}

