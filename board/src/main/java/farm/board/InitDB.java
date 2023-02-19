package farm.board;

import farm.board.domain.Member;
import farm.board.domain.Role;
import farm.board.domain.RoleType;
import farm.board.exception.RoleNotFoundException;
import farm.board.repository.member.MemberRepository;
import farm.board.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component // 해당 객체를 어플리케이션 내에서 사용할 수 있도록 Bean으로 관리하는 역할
@RequiredArgsConstructor
@Slf4j
@Profile("local") // InitDB 클래스가 local 프로파일에서만 실행되도록 설정하는 역할
public class InitDB {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct //객체 생성 후 초기화를 수행하는 메소드를 정의
    @Transactional
    public void initDB(){
        log.info("initialize database");
        initRole();
        initTestAdmin();
        initTestMember();
    }

    private void initRole(){
        roleRepository.saveAll( // 모든 Role_Type role에 저장
                List.of(RoleType.values()).stream().map(roleType -> new Role(roleType)).collect(Collectors.toList())
        );
    }

    private void initTestAdmin() {
        memberRepository.save(
                new Member("admin@admin.com", passwordEncoder.encode("123456a!"), "admin", "admin",
                        List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                                roleRepository.findByRoleType(RoleType.ROLE_ADMIN).orElseThrow(RoleNotFoundException::new)))
        );
    }

    private void initTestMember() {
        memberRepository.saveAll(
                List.of(
                        new Member("member1@member.com", passwordEncoder.encode("123456a!"), "member1", "member1",
                                List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))),
                        new Member("member2@member.com", passwordEncoder.encode("123456a!"), "member2", "member2",
                                List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))))
        );
    }
}
