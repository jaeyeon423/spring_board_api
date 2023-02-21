package farm.board.repository.role;

import farm.board.domain.Role;
import farm.board.domain.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static farm.board.factory.domain.RoleFactory.createRole;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@DataJpaTest
class RoleRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private void persist(Role role) {
        em.persist(role);
    }

    private void flush() {
        em.flush();
        em.clear();
    }

    @Test
    void testFindByRoleType() {
        // given
        Role role = createRole();
        persist(role);
        flush();

        // when
        Optional<Role> found = roleRepository.findByRoleType(RoleType.ROLE_ADMIN);

        // then
        assertTrue(found.isPresent());
        assertThat(found.get().getRoleType()).isEqualTo(RoleType.ROLE_ADMIN);
    }

    @Test
    void testFindByRoleTypeShouldReturnEmpty() {
        // given
        Role role = new Role(RoleType.ROLE_NORMAL);
        persist(role);
        flush();

        // when
        Optional<Role> found = roleRepository.findByRoleType(RoleType.ROLE_ADMIN);

        // then
        assertFalse(found.isPresent());
    }

    @Test
    void testSaveRole() {
        // given
        Role role = new Role(RoleType.ROLE_ADMIN);

        // when
        Role savedRole = roleRepository.save(role);

        // then
        assertNotNull(savedRole);
        assertNotNull(savedRole.getId());
    }

    @Test
    void testDeleteRole() {
        // given
        Role role = new Role(RoleType.ROLE_ADMIN);
        persist(role);
        flush();

        // when
        roleRepository.delete(role);
        Optional<Role> found = roleRepository.findById(role.getId());

        // then
        assertFalse(found.isPresent());
    }

}