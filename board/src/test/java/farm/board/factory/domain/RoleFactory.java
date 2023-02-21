package farm.board.factory.domain;

import farm.board.domain.Role;
import farm.board.domain.RoleType;

public class RoleFactory {
    public static Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }
}
