package farm.board;

import farm.board.domain.Role;
import farm.board.domain.RoleType;
import farm.board.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
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

    @PostConstruct //객체 생성 후 초기화를 수행하는 메소드를 정의
    @Transactional
    public void initDB(){
        log.info("initialize database");
        initRole();
    }

    private void initRole(){
        roleRepository.saveAll( // 모든 Role_Type role에 저장
                List.of(RoleType.values()).stream().map(roleType -> new Role(roleType)).collect(Collectors.toList())
        );
    }
}
