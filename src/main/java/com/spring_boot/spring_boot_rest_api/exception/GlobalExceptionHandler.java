package com.spring_boot.spring_boot_rest_api.exception;

import com.spring_boot.spring_boot_rest_api.utils.APIResponse;
import com.spring_boot.spring_boot_rest_api.utils.ErrorDetails;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler  {

    /**
     * Handles MethodArgumentNotValidException and returns a custom error response.
     *
     * @param ex the exception
     * @return a ResponseEntity with the error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDetails> errorDetails = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ErrorDetails(error.getField(), error.getDefaultMessage()))
                .toList();
        APIResponse<?> response = APIResponse.builder()
                .status("error")
                .message("Invalid input data")
                .data(null)
                .errors(errorDetails)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handles AccessDeniedException and returns a custom error response.
     *
     * @param ex the exception
     * @return a ResponseEntity with the error response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        APIResponse<?> response = APIResponse.builder()
                .status("error")
                .message("Access denied")
                .data(null)
                .errors(List.of(new ErrorDetails("access", ex.getMessage())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles BadCredentialsException and returns a custom error response.
     *
     * @param ex the exception
     * @return a ResponseEntity with the error response
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse<?>> handleBadCredentialsException(BadCredentialsException ex) {
        APIResponse<?> response = APIResponse.builder()
                .status("error")
                .message("Invalid credentials")
                .data(null)
                .errors(List.of(new ErrorDetails("credentials", ex.getMessage())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles AuthenticationException and returns a custom error response.
     *
     * @param ex the exception
     * @return a ResponseEntity with the error response
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse<?>> handleAuthenticationException(AuthenticationException ex) {
        APIResponse<?> response = APIResponse.builder()
                .status("error")
                .message("Authentication failed")
                .data(null)
                .errors(List.of(new ErrorDetails("authentication", ex.getMessage())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles general exceptions and returns a custom error response.
     *
     * @param ex the exception
     * @return a ResponseEntity with the error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleGeneralException(Exception ex) {
        APIResponse<?> response = APIResponse.builder()
                .status("error")
                .message("An error occurred")
                .data(null)
                .errors(List.of(new ErrorDetails("error", ex.getMessage())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles RuntimeException and returns a custom error response.
     *
     * @param ex the exception
     * @return a ResponseEntity with the error response
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIResponse<?>> handleRuntimeException(RuntimeException ex) {
        APIResponse<?> response = APIResponse.builder()
                .status("error")
                .message("An error occurred")
                .data(null)
                .errors(List.of(new ErrorDetails("error", ex.getMessage())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles EntityNotFoundException and returns a custom error response.
     *
     * @param ex the exception
     * @return a ResponseEntity with the error response
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleEntityNotFoundException(EntityNotFoundException ex) {
        APIResponse<?> response = APIResponse.builder()
                .status("error")
                .message("Entity not found")
                .data(null)
                .errors(List.of(new ErrorDetails("entity", ex.getMessage())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles IllegalArgumentException and returns a custom error response.
     *
     * @param ex the exception
     * @return a ResponseEntity with the error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        APIResponse<?> response = APIResponse.builder()
                .status("error")
                .message("Invalid argument")
                .data(null)
                .errors(List.of(new ErrorDetails("argument", ex.getMessage())))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
