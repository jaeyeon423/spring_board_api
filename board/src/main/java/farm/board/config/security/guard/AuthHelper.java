package farm.board.config.security.guard;

import farm.board.config.security.CustomAuthenticationToken;
import farm.board.config.security.CustomUserDetails;
import farm.board.domain.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthHelper {

    public boolean isAuthenticated() {
        log.info("1. {}", getAuthentication());

        return getAuthentication() instanceof CustomAuthenticationToken &&
                getAuthentication().isAuthenticated();
    }

    public Long extractMemberId() {
        return Long.valueOf(getUserDetails().getUserId());
    }

    public Set<RoleType> extractMemberRoles() {
        return getUserDetails().getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .map(strAuth -> RoleType.valueOf(strAuth))
                .collect(Collectors.toSet());
    }

    public boolean isAccessTokenType() {
        log.info("isAccessTokenType");
        return "access".equals(((CustomAuthenticationToken) getAuthentication()).getType());
    }

    public boolean isRefreshTokenType() {
        return "refresh".equals(((CustomAuthenticationToken) getAuthentication()).getType());
    }

    private CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
