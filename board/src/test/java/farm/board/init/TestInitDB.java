package farm.board.init;

import farm.board.domain.Member;
import farm.board.domain.Role;
import farm.board.domain.RoleType;
import farm.board.exception.RoleNotFoundException;
import farm.board.repository.member.MemberRepository;
import farm.board.repository.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component //bean으로 등록
public class TestInitDB {
    @Autowired RoleRepository roleRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    private String adminEmail = "admin@admin.com";
    private String member1Email = "member1@member.com";
    private String member2Email = "member2@member.com";
    private String password = "123456a!";

    @Transactional
    public void initDB() {
        initRole(); // RoleType enum의 모든 값을 role에 저장
        initTestAdmin(); //관리자 계정 추가
        initTestMember(); // 멤버 계정 추가
    }

    private void initRole() {
        roleRepository.saveAll(
                List.of(RoleType.values()).stream().map(roleType -> new Role(roleType)).collect(Collectors.toList())
        );
    }

    private void initTestAdmin() {
        memberRepository.save(
                new Member(adminEmail, passwordEncoder.encode(password), "admin", "admin",
                        List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                                roleRepository.findByRoleType(RoleType.ROLE_ADMIN).orElseThrow(RoleNotFoundException::new)))
        );
    }

    private void initTestMember() {
        memberRepository.saveAll(
                List.of(
                        new Member(member1Email, passwordEncoder.encode(password), "member1", "member1",
                                List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))),
                        new Member(member2Email, passwordEncoder.encode(password), "member2", "member2",
                                List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))))
        );
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getMember1Email() {
        return member1Email;
    }

    public String getMember2Email() {
        return member2Email;
    }

    public String getPassword() {
        return password;
    }
}