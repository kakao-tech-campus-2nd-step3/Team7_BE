package team7.inplace.cicd;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/cicd")
    public ResponseEntity test() {
        return ResponseEntity.ok("ci/cd 테스트2");
    }
}
