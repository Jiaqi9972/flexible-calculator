package org.ebay.flexiblecalculator.exception;

import lombok.extern.slf4j.Slf4j;
import org.ebay.flexiblecalculator.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;
import java.util.stream.Collectors;


/**
 * Global exception handler for the calculator application
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle calculation exceptions
     */
    @ExceptionHandler(CalculationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleCalculationException(CalculationException ex) {
        log.error("Calculation error occurred: {}", ex.getMessage());

        return Response.builder()
                .code("400")
                .message(ex.getMessage())
                .build();
    }

    /**
     * Handle unsupported operation exceptions
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleUnsupportedOperationException(UnsupportedOperationException ex) {
        log.error("Unsupported operation: {}", ex.getMessage());

        return Response.builder()
                .code("400")
                .message("Unsupported operation: " + ex.getMessage())
                .build();
    }

    /**
     * Handle JSON parsing errors (including invalid enum values)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("JSON parse error: {}", ex.getMessage());

        String message = "Invalid request format";
        // Check for enum conversion errors
        if (ex.getMessage().contains("Cannot deserialize value of type")) {
            message = "Invalid operation value. Supported operations are: ADD, SUBTRACT, MULTIPLY, DIVIDE";
        }

        return Response.builder()
                .code("400")
                .message(message)
                .build();
    }

    /**
     * Handle validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ?
                                fieldError.getDefaultMessage() : "Invalid value"
                ));

        log.error("Validation errors: {}", errors);

        return Response.builder()
                .code("400")
                .message("Invalid parameters")
                .data(errors)
                .build();
    }

    /**
     * Handle type mismatch exceptions
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Type mismatch: {}", ex.getMessage());

        return Response.builder()
                .code("400")
                .message("Parameter '" + ex.getName() + "' has invalid value: " + ex.getValue())
                .build();
    }

    /**
     * Handle missing parameter exceptions
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("Missing parameter: {}", ex.getMessage());

        return Response.builder()
                .code("400")
                .message("Missing required parameter: " + ex.getParameterName())
                .build();
    }

    /**
     * Handle 404 errors
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("No handler found: {}", ex.getMessage());

        return Response.builder()
                .code("404")
                .message("Resource not found: " + ex.getRequestURL())
                .build();
    }

    /**
     * Handle illegal argument exceptions
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument: {}", ex.getMessage());

        return Response.builder()
                .code("400")
                .message("Invalid input: " + ex.getMessage())
                .build();
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);

        return Response.builder()
                .code("500")
                .message("An unexpected error occurred. Please try again later.")
                .build();
    }
}