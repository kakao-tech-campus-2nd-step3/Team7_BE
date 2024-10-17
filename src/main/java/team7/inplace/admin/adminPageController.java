package team7.inplace.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminPageController {
    @GetMapping("/admin")
    public String adminPage(){
        return "admin.html";
    }
}
