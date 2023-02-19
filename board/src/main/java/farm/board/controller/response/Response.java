package farm.board.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) // 필드에 null 값을 가지고 있을 때 JSON 출력에서 해당 필드를 생략
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // 모든 필드를 포함한 생성자를 생성, 접근 권한이 private 이기 때문에 클래스 외부에서는 호출 불가
@Getter
public class Response {
    private boolean success;
    private int code;
    private Result result;

    public static Response success() { // 회원가입이 성공하면 성공 여부만 보여준다.
        return new Response(true, 0, null);
    }

    public static <T> Response success(T data) { // 로그인이 성공하면 성공 데이터를 보여준다.
        return new Response(true, 0, new Success<>(data));
    }

    public static Response failure(int code, String msg) { // 실패시에 실패 코드와 메세지를 보여준다.
        return new Response(false, code, new Failure(msg));
    }
}