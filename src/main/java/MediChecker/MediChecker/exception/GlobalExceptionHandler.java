package MediChecker.MediChecker.exception;
import MediChecker.MediChecker.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(HttpServletRequest request, MethodArgumentNotValidException ex) {
        String path = request.getRequestURI();

        if (path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/api-docs")) {
            throw new RuntimeException(ex);
        }
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            validationErrors.put(field, message);
        });

        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Dữ liệu đầu vào không hợp lệ")
                .validationErrors(validationErrors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle validation errors from @RequestParam, @PathVariable
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(HttpServletRequest request,ConstraintViolationException ex) {
        String path = request.getRequestURI();
        if (path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/api-docs")) {
            throw new RuntimeException(ex);
        }
        Map<String, String> violations = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String field = cv.getPropertyPath().toString();
            String message = cv.getMessage();
            violations.put(field, message);
        });

        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Constraint Violation")
                .message("Tham số không hợp lệ")
                .validationErrors(violations)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Catch all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(HttpServletRequest request,Exception ex) {
        log.error("Unexpected error occurred", ex); // Log lỗi ra console
        String path = request.getRequestURI();
        if (path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/api-docs")) {
            throw new RuntimeException(ex);
        }
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Đã xảy ra lỗi hệ thống")
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
