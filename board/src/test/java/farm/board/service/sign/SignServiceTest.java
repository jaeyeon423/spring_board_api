package farm.board.service.sign;

import farm.board.domain.Member;
import farm.board.domain.Role;
import farm.board.domain.RoleType;
import farm.board.dto.sign.RefreshTokenResponse;
import farm.board.dto.sign.SignInRequest;
import farm.board.dto.sign.SignInResponse;
import farm.board.dto.sign.SignUpRequest;
import farm.board.exception.*;
import farm.board.repository.member.MemberRepository;
import farm.board.repository.role.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static farm.board.factory.domain.MemberFactory.createMember;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignServiceTest {

    @InjectMocks SignService signService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock TokenService tokenService;

    @Test
    public void signUpTest(){ //정상적인 회원가입 로직 테스트
        //given
        SignUpRequest req = createSignUpRequest();
        given(roleRepository.findByRoleType(RoleType.ROLE_NORMAL)).willReturn(Optional.of(new Role(RoleType.ROLE_NORMAL)));

        //when
        signService.signUp(req); 

        //then
        verify(passwordEncoder).encode(req.getPassword());
        verify(memberRepository).save(any());
    }
    @Test
    void validateSignUpByDuplicateEmailTest() { //이메일 중복 검증 테스트
        // given
        given(memberRepository.existsByEmail(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> signService.signUp(createSignUpRequest()))
                .isInstanceOf(MemberEmailAlreadyExistsException.class);
    }

    @Test
    void validateSignUpByDuplicateNicknameTest() { //닉네임 중복 검증 테스트 
        // given
        given(memberRepository.existsByNickname(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> signService.signUp(createSignUpRequest()))
                .isInstanceOf(MemberNicknameAlreadyExistsException.class);
    }

    @Test
    void signUpRoleNotFoundTest() { //등록되지 않은 권한 등급으로 회원 가입시에 해당 권한 등급을 찾을 수 없습니다. 
        // given
        given(roleRepository.findByRoleType(RoleType.ROLE_NORMAL)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> signService.signUp(createSignUpRequest()))
                .isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    void signInTest() {  //정상적인 로그인 로직
        // given
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(createMember()));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(tokenService.createAccessToken(anyString())).willReturn("access");
        given(tokenService.createRefreshToken(anyString())).willReturn("refresh");

        // when
        SignInResponse res = signService.signIn(new SignInRequest("email", "password"));

        // then
        assertThat(res.getAccessToken()).isEqualTo("access");
        assertThat(res.getRefreshToken()).isEqualTo("refresh");
    }

    @Test
    void signInExceptionByNoneMemberTest() { //등록된 이메일이 아닐경우 에러 발생
        // given
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> signService.signIn(new SignInRequest("email", "password")))
                .isInstanceOf(LoginFailureException.class);
    }

    @Test
    void signInExceptionByInvalidPasswordTest() { //비밀번호가 잘못되었다면 에러 발생
        // given
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(createMember()));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when, then
        assertThatThrownBy(() -> signService.signIn(new SignInRequest("email", "password")))
                .isInstanceOf(LoginFailureException.class);
    }

    @Test
    void refreshTokenTest() {
        // given
        String refreshToken = "refreshToken";
        String subject = "subject";
        String accessToken = "accessToken";
        given(tokenService.validateRefreshToken(refreshToken)).willReturn(true);
        given(tokenService.extractRefreshTokenSubject(refreshToken)).willReturn(subject);
        given(tokenService.createAccessToken(subject)).willReturn(accessToken);

        // when
        RefreshTokenResponse res = signService.refreshToken(refreshToken);

        // then
        assertThat(res.getAccessToken()).isEqualTo(accessToken);
    }

    @Test
    void refreshTokenExceptionByInvalidTokenTest() {
        // given
        String refreshToken = "refreshToken";
        given(tokenService.validateRefreshToken(refreshToken)).willReturn(false);

        // when, then
        assertThatThrownBy(() -> signService.refreshToken(refreshToken))
                .isInstanceOf(AuthenticationEntryPointException.class);
    }


    private SignUpRequest createSignUpRequest() {
        return new SignUpRequest("email", "password", "username", "nickname");
    }


}