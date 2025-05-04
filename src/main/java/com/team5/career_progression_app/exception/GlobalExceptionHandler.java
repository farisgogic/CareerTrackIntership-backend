package com.team5.career_progression_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "error", "internal_server_error",
                "message", ex.getMessage()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "error", "not_found",
                "message", ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "error", "authentication_error",
                "message", ex.getMessage()
        ), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "error", "access_denied",
                "message", ex.getMessage()
        ), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateAssignmentException.class)
    public ResponseEntity<?> handleDuplicateAssignmentException(DuplicateAssignmentException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "error", "duplicate_assignment",
                "message", ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyActiveException.class)
    public ResponseEntity<?> handleUserAlreadyActiveException(UserAlreadyActiveException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "error", "user_already_active",
                "message", ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenVerificationException.class)
    public ResponseEntity<?> handleTokenVerificationException(TokenVerificationException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "error", "token_verification_error",
                "message", ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenRevocationException.class)
    public ResponseEntity<?> handleTokenRevocationException(TokenRevocationException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "error", "token_revocation_error",
                "message", ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }
}
