package practice.sessionlogin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    // GET /signup 요청에 대해 회원 가입 페이지로 응답함
    @GetMapping("/signup")
    public String signupPage() {
        return "members/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequest params) {
        // DB에 저장
        memberRepository.save(
                new Member(
                        params.email(),
                        params.nickname(),
                        params.password()
                )
        );

        return "redirect:/";
    }

    // GET /login 요청에 대해 로그인 페이지로 응답함
    @GetMapping("/login")
    public String loginPage() {
        return "members/login";
    }
}
