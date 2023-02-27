package farm.board.config.security;

import farm.board.domain.member.Member;
import farm.board.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findWithRolesById(Long.valueOf(userId))
                .orElseGet(() -> new Member(null, null, null, null, List.of()));
        return new CustomUserDetails(
                String.valueOf(member.getId()),
                getAuthorities(member)
        );
    }

    private Set<GrantedAuthority> getAuthorities(Member member) {
        return member.getRoles().stream().map(memberRole -> memberRole.getRole())
                .map(role -> role.getRoleType())
                .map(roleType -> roleType.toString())
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
