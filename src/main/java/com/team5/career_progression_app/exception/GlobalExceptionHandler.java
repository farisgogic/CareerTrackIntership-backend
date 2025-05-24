package com.team5.career_progression_app.exception;

import com.team5.career_progression_app.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
        
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "internal_server_error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
                        WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "not_found", ex.getMessage()), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex,
                        WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "authentication_error", ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "access_denied", ex.getMessage()), HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler(DuplicateAssignmentException.class)
        public ResponseEntity<ErrorResponse> handleDuplicateAssignmentException(DuplicateAssignmentException ex,
                        WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "duplicate_assignment", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(UserAlreadyActiveException.class)
        public ResponseEntity<ErrorResponse> handleUserAlreadyActiveException(UserAlreadyActiveException ex,
                        WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "user_already_active", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(TokenVerificationException.class)
        public ResponseEntity<ErrorResponse> handleTokenVerificationException(TokenVerificationException ex,
                        WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "token_verification_error", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(TokenRevocationException.class)
        public ResponseEntity<ErrorResponse> handleTokenRevocationException(TokenRevocationException ex,
                        WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "token_revocation_error", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(InvalidRequestException.class)
        public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex,
                        WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "bad_request", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(NotificationNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotificationNotFoundException(NotificationNotFoundException ex,
                        WebRequest request) {
                return new ResponseEntity<>(new ErrorResponse(
                                "notification_not_found", ex.getMessage()), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(SkillAlreadyExistsException.class)
        public ResponseEntity<ErrorResponse> handleSkillAlreadyExistsException(
                        SkillAlreadyExistsException ex, WebRequest request) {
                return new ResponseEntity<>(
                                new ErrorResponse("skill_already_exists", ex.getMessage()),
                                HttpStatus.CONFLICT);
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
                return new ResponseEntity<>(
                        new ErrorResponse("user_not_found", ex.getMessage()),
                        HttpStatus.NOT_FOUND
                );
        }

        @ExceptionHandler(RoleNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex, WebRequest request) {
                return new ResponseEntity<>(
                        new ErrorResponse("role_not_found", ex.getMessage()),
                        HttpStatus.NOT_FOUND
                );
        }
}
