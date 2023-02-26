package farm.board.repository.member;

import farm.board.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;

import static farm.board.factory.domain.MemberFactory.createMember;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // JPA 관련 구성만 로드하여 테스트
class     MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    public void saveMemberTest(){
        // given
        Member member = createMember();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(savedMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(savedMember.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    void saveMemberWithDuplicateEmailTest() {
        // given
        Member member = createMember();
        memberRepository.save(member);
        // when
        Member duplicateEmailMember = new Member(member.getEmail(), "new_password",  "new_username","new_nickname", Collections.emptyList());
        assertThrows(DataIntegrityViolationException.class, () -> memberRepository.save(duplicateEmailMember));
    }

    @Test
    void saveMemberWithDuplicateNicknameTest() {
        // given
        String email1 = "test1@test.com";
        String email2 = "test2@test.com";
        String password = "password";
        String nickname = "test_nickname";
        String username1 = "test_username1";
        String username2 = "test_username2";
        Member member1 = new Member(email1, password, username1, nickname, Collections.emptyList());
        Member member2 = new Member(email2, password, username2, nickname, Collections.emptyList());
        memberRepository.save(member1);
        // when, then
        assertThrows(DataIntegrityViolationException.class, () -> memberRepository.save(member2));
    }
}