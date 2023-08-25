package hello.api.presentation;

import hello.core.application.MemberService;
import hello.core.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("members/{id}")
    public Member findMember(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }
}
