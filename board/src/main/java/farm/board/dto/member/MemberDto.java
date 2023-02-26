package farm.board.dto.member;

import farm.board.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    public Long id;
    private String email;
    private String username;
    private String nickname;

    public static MemberDto toDto(Member member){
        return new MemberDto(member.getId(), member.getEmail(), member.getUsername(), member.getNickname());
    }
}
