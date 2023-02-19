package farm.board.controller.member;

import farm.board.controller.response.Response;
import farm.board.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@PathVariable Long id){
        return Response.success(memberService.read(id)); //회원 조회 후 테이터와 함께 성공 반환
    }

    @DeleteMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        memberService.delete(id); //회원 삭제
        return Response.success(); //성공 반환
    }
}
