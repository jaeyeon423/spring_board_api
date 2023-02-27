package farm.board.repository.member;

import farm.board.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); //Optional은 조회 결과가 null인 경우를 대비해줌
    Optional<Member> findByNickname(String nickname);

    @EntityGraph("Member.roles")
    Optional<Member> findWithRolesById(Long id);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
