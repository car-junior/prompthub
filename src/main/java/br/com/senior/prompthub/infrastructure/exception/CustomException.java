package br.com.senior.prompthub.infrastructure.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomException extends RuntimeException {
    private final String message;
    @Getter
    private final HttpStatus httpStatus;

    @Builder
    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static CustomException badRequest(String message) {
        return CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .build();
    }

    public static CustomException notFound(String message) {
        return CustomException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(message)
                .build();
    }

    public Map<String, Object> getHandleCustomException() {
        var responseException = new LinkedHashMap<String, Object>();
        responseException.put("code", httpStatus.value());
        responseException.put("message", message);
        return responseException;
    }
}
