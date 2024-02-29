package edu.java.controller;

import edu.java.models.dto.ErrorResponse;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handler(Exception ex) {
        var stacktrace = Arrays.stream(ex.getStackTrace()).map(Objects::toString).toList();
        var errorResponse = new ErrorResponse(
            "400",
            "Неверный формат url",
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            stacktrace
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
