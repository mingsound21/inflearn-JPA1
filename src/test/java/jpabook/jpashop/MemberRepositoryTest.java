package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) // jUnit에게 Spring관련된 것 테스트 함을 알림
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional // em을 통한 데이터 변경은 항상 transaction안에서 이뤄져야함
    // transactional이 test 어노테이션에 있으면 test 끝난 뒤, 무조건 rollback함
    @Rollback(value = false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setName("memberA");
        
        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(member.getId());

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member); // true (조회한 것 = 저장한 것)
        System.out.println("findMember == member : " + (findMember == member));
        // true인 이유
        // 같은 트랜잭션안에서 저장하고 조회하면 영속성 컨텍스트가 같음
        // 같은 영속성 컨텍스트 안에서 식별자가 같으면 같은 엔티티로 인식
       }
}