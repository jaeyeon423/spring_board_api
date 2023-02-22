package farm.board.controller.sign;

import farm.board.controller.response.Response;
import farm.board.dto.sign.SignInRequest;
import farm.board.dto.sign.SignUpRequest;
import farm.board.service.sign.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static farm.board.controller.response.Response.success;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor // 클래스에서 선언된 final 필드를 가지고 생성자를 만들어줌
public class SignController {
    private final SignService signService;

    @PostMapping("/api/sign-up") //HTTP POST 요청을 처리하는 핸들러 메소드를 지정
    @ResponseStatus(HttpStatus.CREATED) //메소드의 실행 결과에 대한 HTTP 응답 코드를 HpptStatus.CREATED로 설정
    public Response signUp(@Valid @RequestBody SignUpRequest req){ //@Valid : SignUpRequest 객체에 대한 유효성 검사, @RequestBody : 청 바디에서 데이터를 추출하여, 매개변수로 전달된 객체에 매핑
        signService.signUp(req); // 회원 가입 처리
        return success();
    }

    @PostMapping("/api/sign-in")
    @ResponseStatus(HttpStatus.OK)//메소드의 실행 결과에 대한 HTTP 응답 코드를 HpptStatus.OK로 설정
    public Response signIn(@Valid @RequestBody SignInRequest req) {
        return success(signService.signIn(req)); // 로그인 처리
    }

    @PostMapping("/api/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public Response refreshToken(@RequestHeader(value = "Authorization") String refreshToken) {
        return success(signService.refreshToken(refreshToken));
    }
}
