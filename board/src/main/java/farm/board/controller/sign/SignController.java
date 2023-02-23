package farm.board.controller.sign;

import farm.board.controller.response.Response;
import farm.board.dto.sign.SignInRequest;
import farm.board.dto.sign.SignUpRequest;
import farm.board.service.sign.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import static farm.board.controller.response.Response.success;

@Api(value = "Sign Controller", tags = "Sign") // SignController API를 대표하는 value와 tage 정보 설정
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor // 클래스에서 선언된 final 필드를 가지고 생성자를 만들어줌
public class SignController {
    private final SignService signService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.") //API 문서에서 aPI의 이름(value)와 설명(notes)를 설정
    @PostMapping("/api/sign-up") //HTTP POST 요청을 처리하는 핸들러 메소드를 지정
    @ResponseStatus(HttpStatus.CREATED) //메소드의 실행 결과에 대한 HTTP 응답 코드를 HpptStatus.CREATED로 설정
    public Response signUp(@Valid @RequestBody SignUpRequest req){ //@Valid : SignUpRequest 객체에 대한 유효성 검사, @RequestBody : 청 바디에서 데이터를 추출하여, 매개변수로 전달된 객체에 매핑
        signService.signUp(req); // 회원 가입 처리
        return success();
    }

    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    @PostMapping("/api/sign-in")
    @ResponseStatus(HttpStatus.OK)//메소드의 실행 결과에 대한 HTTP 응답 코드를 HpptStatus.OK로 설정
    public Response signIn(@Valid @RequestBody SignInRequest req) {
        return success(signService.signIn(req)); // 로그인 처리
    }

    @ApiOperation(value = "토큰 재발급", notes = "리프레시 토큰으로 새로운 액세스 토큰을 발급 받는다.")
    @PostMapping("/api/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public Response refreshToken(@ApiIgnore @RequestHeader(value = "Authorization") String refreshToken) {
        return success(signService.refreshToken(refreshToken));
    }
}
