package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/* @Controller: 해당 객체를 생성해서 스프링 컨테이너에 넣어두고 스프링이 관리한다.
    => '스프링 빈이 관리된다.'고 한다.*/
@Controller
public class MemberController {

    private final MemberService memberService;

    // 생성자에 @Autowired를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아 주입한다.
    // 여기서는 스프링 컨테이너에서 memberService 스프링 빈을 찾아 주입.
    // 생성자가 1개만 있으면 @Autowired는 생략할 수 있다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")  //GET 방식
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")  // POST 방식
    public String create(MemberForm form) {

        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members=memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
