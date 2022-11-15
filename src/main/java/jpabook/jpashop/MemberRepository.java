package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    @PersistenceContext // 스프링부트가 EntityManager를 주입해줌
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
    // command와 query를 분리
    // side effect 일으키는 command라서 return 값을 거의 안만드는게 좋음
    // 그래서 id 정도만 반환

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
