package practice.sessionlogin;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.UUID;

@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    public MemberController(MemberRepository memberRepository, SessionManager sessionManager) {
        this.memberRepository = memberRepository;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/")
    public String mainPage(
            @CookieValue(name = "SESSIONID", required = false) String sessionId,
            Model model
    ) {
        Map<String, Object> session = sessionManager.getSession(sessionId, false);
        if (session == null) {
            return "index";
        }

        Member member = (Member) session.get("loginMember");
        model.addAttribute("loggedIn", true);
        model.addAttribute("nickname", member.getNickname());
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

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginRequest params,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response
    ) {
        Member member = memberRepository.findByEmail(params.email())
                .orElse(null);
        // email, password 검증
        if (member == null || !member.authenticate(params.password())) {
            redirectAttributes.addFlashAttribute("error", "ID 또는 PW가 일치하지 않습니다.");
            return "redirect:/login";
        }

        // 로그인 성공 처리
        String sessionId = UUID.randomUUID().toString();
        Map<String, Object> session = sessionManager.getSession(sessionId, true);
        session.put("loginMember", member);
        // Set-Cookie: SESSIONID=59b4a27c-14b3-44a2-9d49-ffb4b8783a5e
        response.addCookie(new Cookie("SESSIONID", sessionId));

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(name = "SESSIONID", required = false) String sessionId) {
        sessionManager.invalidate(sessionId);

        return "redirect:/";
    }
}
