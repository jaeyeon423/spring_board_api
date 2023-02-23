package farm.board.controller.member;

import farm.board.controller.response.Response;
import farm.board.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "Member Controller", tags = "Member")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "사용자 정보 조회", notes = "사용자 정보를 조회한다.")
    @GetMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@PathVariable Long id){
        return Response.success(memberService.read(id)); //회원 조회 후 테이터와 함께 성공 반환
    }

    @ApiOperation(value = "사용자 정보 삭제", notes = "사용자 정보를 삭제한다.")
    @DeleteMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        memberService.delete(id); //회원 삭제
        return Response.success(); //성공 반환
    }
}
