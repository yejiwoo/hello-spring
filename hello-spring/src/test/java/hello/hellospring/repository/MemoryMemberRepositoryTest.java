package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository=new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){

        //given
        Member member=new Member();
        member.setName("spring");

        //when
        repository.save(member);

        //then
        Member result=repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName(){
        //given
        Member memeber1=new Member();
        memeber1.setName("spring1");
        repository.save(memeber1);

        Member memeber2=new Member();
        memeber2.setName("spring2");
        repository.save(memeber2);

        //when
        Member result = repository.findByName("spring1").get();

        //then
        assertThat(result).isEqualTo(memeber1);
    }

    @Test
    public void findAll(){
        //given
        Member memeber1=new Member();
        memeber1.setName("spring1");
        repository.save(memeber1);

        Member memeber2=new Member();
        memeber2.setName("spring2");
        repository.save(memeber2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}
