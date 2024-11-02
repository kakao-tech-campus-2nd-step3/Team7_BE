package team7.inplace.admin;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team7.inplace.global.exception.ErrorLog;
import team7.inplace.global.exception.ErrorLogRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPageController {
    private final ErrorLogRepository errorLogRepository;


    @GetMapping("/video")
    public String adminPage() {
        return "admin/video.html";
    }

    @GetMapping("/error-logs")
    public String getErrorLogs(Model model) {
        List<ErrorLog> errorLogs = errorLogRepository.findByIsResolvedFalse();
        model.addAttribute("errorLogs", errorLogs);
        return "admin/error-logs.html";
    }
}
