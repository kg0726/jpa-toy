package jpa.practice.toy.controller;

import jpa.practice.toy.dto.ErrorResponse;
import jpa.practice.toy.exception.AuthorizationException;
import jpa.practice.toy.exception.ItemNotFoundException;
import jpa.practice.toy.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

@RestControllerAdvice
public class ExControllerAdvice {
    private final View error;

    public ExControllerAdvice(View error) {
        this.error = error;
    }

    // @Valid 검증 실패 시 발생하는 MethodArgumentNotValidException을 잡음
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        // 발생한 에러들 중 첫 번째 에러의 메시지를 가져옴
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorBody = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException e) {
        String errorMessage = e.getMessage(); // 에러 메시지를 가져옴
        ErrorResponse errorBody = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND_MEMBER",
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException e) {
        String errorMessage = e.getMessage();

        ErrorResponse errorBody = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND_ITEM",
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handelAAuthorizationException(AuthorizationException e) {
        String errorMessage = e.getMessage();

        ErrorResponse errorBody = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody);
    }
}
