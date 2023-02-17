package farm.board.domain;

import farm.board.domain.common.EntityDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static farm.board.domain.RoleType.ROLE_NORMAL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends EntityDate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(nullable = false, length = 30, unique = true)
    private String email;
    private String password;
    @Column(nullable = false, length = 20)
    private String username;
    @Column(nullable = false, unique = true, length = 20)
    private String nickname;
    @OneToMany(mappedBy = "member")
    private List<MemberRole> roles = new ArrayList<>();
    public Member(String email, String password, String username, String nickname) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }
}
