package team7.inplace.global.exception;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String errorUrl;
    private String errorMessage;
    @Column(columnDefinition = "TEXT")
    private String stackTrace;

    private boolean isResolved;

    private ErrorLog(String errorUrl, String errorMessage, String stackTrace) {
        this.errorUrl = errorUrl;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
        this.isResolved = false;
    }

    public static ErrorLog of(String url, String message, String stackTrace) {
        return new ErrorLog(url, message, stackTrace);
    }

    public void resolve() {
        this.isResolved = true;
    }
}
