package team7.inplace.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class InplaceExceptionHandler {
    private final ErrorLogRepository errorLogRepository;

    @ExceptionHandler(InplaceException.class)
    public ResponseEntity<ProblemDetail> handleInplaceException(
            HttpServletRequest request,
            InplaceException exception
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                exception.getHttpStatus(),
                exception.getMessage()
        );
        problemDetail.setTitle(exception.getErrorCode());
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        return new ResponseEntity<>(problemDetail, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(
            HttpServletRequest request,
            Exception exception
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage()
        );
        problemDetail.setTitle("E999");
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        saveErrorLog(request, exception);
        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void saveErrorLog(HttpServletRequest request, Exception exception) {
        ErrorLog errorLog = ErrorLog.of(
                request.getRequestURI(),
                exception.getMessage(),
                getStackTrace(exception)
        );
        errorLogRepository.save(errorLog);
    }

    public String getStackTrace(Exception exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(exception.toString()).append("\n");
        for (StackTraceElement element : exception.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
