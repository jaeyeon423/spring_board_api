package farm.board.exception;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberNicknameAlreadyExistsException extends RuntimeException{
    public MemberNicknameAlreadyExistsException(String message) {
        super(message);
    }
}

