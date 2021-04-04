package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행. 진짜 스프링을 띄워서 Test 실행.
@Transactional // 테스트케이스에 붙어있으면, 테스트 시작 전 transaction 시작하고 테스트 끝나면 rollback
public class MemberServiceIntegrationTest {

    @Autowired MemberRepository memberRepository;  // 구현체는 SpringConfig에서 설정한 구현체가 올라온다.
    @Autowired MemberService memberService;

    @Test
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring!");

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
}
