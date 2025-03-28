package com.spring_boot.spring_boot_rest_api.exception;

import com.spring_boot.spring_boot_rest_api.utils.APIResponse;
import com.spring_boot.spring_boot_rest_api.utils.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler  {

    // Handle specific exceptions and return custom responses
    // For example, handle UserNotFoundException, InvalidInputException, etc.

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        //String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
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
}
