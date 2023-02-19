package farm.board.service.member;

import farm.board.domain.Member;
import farm.board.dto.member.MemberDto;
import farm.board.exception.MemberNotFoundException;
import farm.board.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks MemberService memberService;
    @Mock
    MemberRepository memberRepository;

    @Test
    public void readTest(){
        //given
        Member member = createMember();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        //when
        MemberDto result = memberService.read(1L);

        //then
        assertThat(result.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void readExceptionByMemberNotFoundTest() {
        // given
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(null));

        // when, then
        assertThatThrownBy(() -> memberService.read(1L)).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void deleteTest() {
        // given
        given(memberRepository.existsById(anyLong())).willReturn(true);

        // when
        memberService.delete(1L);

        // then
        verify(memberRepository).deleteById(anyLong());
    }

    @Test
    void deleteExceptionByMemberNotFoundTest() {
        // given
        given(memberRepository.existsById(anyLong())).willReturn(false);

        // when, then
        assertThatThrownBy(() -> memberService.delete(1L)).isInstanceOf(MemberNotFoundException.class);
    }

    private Member createMember() {
        return new Member("email@email.com", "123456a!", "username", "nickname", List.of());
    }
}