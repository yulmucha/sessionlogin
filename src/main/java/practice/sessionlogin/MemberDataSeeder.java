package practice.sessionlogin;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class MemberDataSeeder {

    private final MemberRepository memberRepository;

    public MemberDataSeeder(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostConstruct
    public void initializeMemberData() {
        memberRepository.save(new Member("dora@gmail.com", "도라에몽", "drdr"));
    }
}
