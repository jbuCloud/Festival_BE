package com.jbucloud.festival.global.response;

import com.jbucloud.festival.global.response.status.SuccessStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private final boolean success;
    @Schema(description = "응답코드", example = "COMMON200")
    private final String code;
    @Schema(description = "응답코드", example = "요청이 성공적으로 처리되었습니다.")
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> of(BaseErrorCode rc, T data) {
        return rc.toResponse(data);
    }

    public static <T> ApiResponse<T> onSuccess(T result){
        return new ApiResponse<>(true, SuccessStatus.OK.getCode() , SuccessStatus.OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<> (false, code, message, data);
    }
}
