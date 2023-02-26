package farm.board.factory.domain;

import farm.board.domain.member.Role;
import farm.board.domain.member.RoleType;

public class RoleFactory {
    public static Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }
}
