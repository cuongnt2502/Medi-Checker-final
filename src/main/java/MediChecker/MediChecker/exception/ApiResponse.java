package MediChecker.MediChecker.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private int status;
    private T data;
    private String error;
    private String message;
    private Map<String, String> validationErrors;
}