package farm.board.service.sign;

import farm.board.domain.Member;
import farm.board.domain.RoleType;
import farm.board.dto.sign.RefreshTokenResponse;
import farm.board.dto.sign.SignInRequest;
import farm.board.dto.sign.SignInResponse;
import farm.board.dto.sign.SignUpRequest;
import farm.board.exception.*;
import farm.board.repository.member.MemberRepository;
import farm.board.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignUpRequest req){
        validateSignUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(
                req,
                roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                passwordEncoder
        ));
    }
    private void validateSignUpInfo(SignUpRequest req) {
        if(memberRepository.existsByEmail(req.getEmail()))
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        if(memberRepository.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest req){
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member);
        String subject = createSubject(member);
        String accessToken = tokenService.createAccessToken(subject);
        String refreshToken = tokenService.createRefreshToken(subject);
        return new SignInResponse(accessToken, refreshToken);
    }

    private void validatePassword(SignInRequest req, Member member) {
        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    public RefreshTokenResponse refreshToken(String rToken) {
        validateRefreshToken(rToken);
        String subject = tokenService.extractRefreshTokenSubject(rToken);
        String accessToken = tokenService.createAccessToken(subject);
        return new RefreshTokenResponse(accessToken);
    }

    private void validateRefreshToken(String rToken) {
        if(!tokenService.validateRefreshToken(rToken)) {
            throw new AuthenticationEntryPointException();
        }
    }

    private String createSubject(Member member) {
        return String.valueOf(member.getId());
    }

}
