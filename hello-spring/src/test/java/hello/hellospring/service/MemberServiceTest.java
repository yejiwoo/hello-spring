package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemberServiceTest {

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        memberRepository=new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long savedId = memberService.join(member);

        //then
        Member foundMember = memberRepository.findById(savedId).get();
        assertThat(foundMember.getName()).isEqualTo(member.getName());
    }

    @Test
    public void 중북회원예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); //예외가 발생해야 한다.

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    public void 회원조회() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberService.join(member2);

        //when
        List<Member> foundMembers = memberService.findMembers();

        //then

    }

}
