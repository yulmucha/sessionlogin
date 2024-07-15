package practice.sessionlogin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "members/signup";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "members/login";
    }
}
