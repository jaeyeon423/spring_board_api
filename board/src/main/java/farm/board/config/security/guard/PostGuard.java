package farm.board.config.security.guard;

import farm.board.domain.member.RoleType;
import farm.board.domain.post.Post;
import farm.board.exception.AccessDeniedException;
import farm.board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostGuard {
    private final AuthHelper authHelper;
    private final PostRepository postRepository;

    public boolean check(Long id){
        return authHelper.isAuthenticated() && hasAuthority(id);
    }

    private boolean hasAuthority(Long id) {
        return hasAdminRole() || isResourceOwner(id);
    }

    private boolean isResourceOwner(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> {throw new AccessDeniedException();});
        Long memberId = authHelper.extractMemberId();
        log.info("isResourceOwner : {}",post.getMember().getId().equals(memberId));
        return post.getMember().getId().equals(memberId);
    }

    private boolean hasAdminRole() {
        log.info("hasAdminRole : {}",authHelper.extractMemberRoles().contains(RoleType.ROLE_ADMIN) );
        return authHelper.extractMemberRoles().contains(RoleType.ROLE_ADMIN);
    }
}
