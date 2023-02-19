package farm.board.exception;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberEmailAlreadyExistsException extends RuntimeException {
    public MemberEmailAlreadyExistsException(String message) {
        super(message);
    }
}
