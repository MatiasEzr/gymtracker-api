package com.matias.gymtracker.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException e, HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                "USER_NOT_FOUND",
                e.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ExistEmailException.class)
    public ResponseEntity<ApiError> handleExistEmailException(ExistEmailException e, HttpServletRequest request) {
        ApiError error = new ApiError(
                "EMAIL_ALREADY_EXISTS",
                e.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        // Extrae todos los errores de validacion
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                // error.getField() -> nombre del campo (ej: title)
                // error.getDefaultMessage() -> mensaje de la anotacion (@NotBlank, etc.)
                .toList();

        ApiError error = new ApiError(
                "VALIDATION_ERROR",
                String.join(", ", errors),
                // Une todos los errores en un solo string
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentialsException(InvalidCredentialsException e, HttpServletRequest  request) {
        ApiError error = new ApiError(
                "INVALID_CREDENTIALS",
                e.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                "INTERNAL_ERROR",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbidden(
            ForbiddenException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                "FORBIDDEN",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicate(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                "DUPLICATE_RESOURCE",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DayAlreadyAssignedException.class)
    public ResponseEntity<ApiError> handleDayAlreadyAssigned(
            DayAlreadyAssignedException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                "DAY_ALREADY_ASSIGNED",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }




}
