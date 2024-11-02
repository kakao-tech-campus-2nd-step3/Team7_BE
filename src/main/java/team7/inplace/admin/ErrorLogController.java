package team7.inplace.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.global.exception.ErrorLogRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/error-logs")
public class ErrorLogController {
    private final ErrorLogRepository errorLogRepository;

    @PostMapping("/{id}/resolve")
    public ResponseEntity<Void> resolveErrorLog(@PathVariable Long id) {
        errorLogRepository.findById(id).ifPresent(errorLog -> {
            errorLog.resolve();
            errorLogRepository.save(errorLog);
        });
        
        return ResponseEntity.ok().build();
    }
}
