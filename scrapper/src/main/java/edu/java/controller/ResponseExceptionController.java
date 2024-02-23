package edu.java.controller;

import edu.java.models.dto.ErrorResponseDTO;
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
        var errorResponseDTO = new ErrorResponseDTO(
            "400",
            "Неверный формат url",
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            stacktrace
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
