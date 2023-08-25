package hello.admin;

import hello.core.application.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {
    private final MemberService memberService;

    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("message", "This is the admin page.");
        return "admin";
    }

    @DeleteMapping("/admin/members/{id}")
    public String deleteMember(@PathVariable Long id, Model model) {
        memberService.deleteMemberById(id);
        model.addAttribute("message", "Member with ID " + id + " was deleted.");
        return "admin";
    }
}
