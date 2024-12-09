package com.stuttgartspeed.backend.adapter.in.exceptionhandler;

import com.stuttgartspeed.backend.adapter.in.web.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ProblemDetail validationProblemDetail =
                ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Validation error");

        List<ValidationError> errors = exception.getFieldErrors()
                .stream()
                .map(violation -> ValidationError.builder()
                        .fieldName(violation.getField())
                        .message(violation.getDefaultMessage())
                        .rejectedValue(Objects.isNull(
                                violation.getRejectedValue()) ?
                                "null":
                                violation.getRejectedValue().toString())
                        .build())
                .collect(Collectors.toList());

        validationProblemDetail.setProperty("errors", errors);
        return validationProblemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .type("https://example.com/problem/resource-not-found")
                .title("Resource not found")
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .instance(request.getDescription(false))
                .build();

        return new ResponseEntity<>(problemDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ProblemDetails> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .type("https://example.com/problem/invalid-request")
                .title("Invalid request")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getDescription(false))
                .build();

        return new ResponseEntity<>(problemDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ProblemDetails> handleOperationNotAllowedException(OperationNotAllowedException ex, WebRequest request) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .type("https://example.com/problem/operation-not-allowed")
                .title("Operation not allowed")
                .status(HttpStatus.FORBIDDEN.value())
                .detail(ex.getMessage())
                .instance(request.getDescription(false))
                .build();

        return new ResponseEntity<>(problemDetails, HttpStatus.FORBIDDEN);
    }
}
