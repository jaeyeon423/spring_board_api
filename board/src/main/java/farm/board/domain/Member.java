package farm.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
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
    private List<Role> roles = new ArrayList<>();
    public Member(String email, String password, String username, String nickname, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }
}
