package farm.board.repository.member;

import farm.board.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // JPA 관련 구성만 로드하여 테스트
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    public void saveMemberTest(){
        // given
        String email = "test@test.com";
        String password = "password";
        String nickname = "test_nickname";
        String username = "test_username";

        Member member = new Member(email, password, username, nickname, null);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo(email);
        assertThat(savedMember.getPassword()).isEqualTo(password);
        assertThat(savedMember.getNickname()).isEqualTo(nickname);
        assertThat(savedMember.getUsername()).isEqualTo(username);
    }

    @Test
    void saveMemberWithDuplicateEmailTest() {
        // given
        String email = "test@test.com";
        String password = "password";
        String nickname = "test_nickname";
        String username = "test_username";
        Member member = new Member(email, password, username, nickname, null);
        memberRepository.save(member);
        // when
        Member duplicateEmailMember = new Member(email, "new_password",  "new_username","new_nickname", null);
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
        Member member1 = new Member(email1, password, username1, nickname, null);
        Member member2 = new Member(email2, password, username2, nickname, null);
        memberRepository.save(member1);
        // when, then
        assertThrows(DataIntegrityViolationException.class, () -> memberRepository.save(member2));
    }
}