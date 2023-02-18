package farm.board.learning;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PasswordEncoderTest {
    PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();

    @Test
    public void encodeWithBcryptTest(){
        //given
        String password = "password";

        //when
        String encodedPassword = passwordEncoder.encode(password);
        boolean isMatch = passwordEncoder.matches(password, encodedPassword);

        //then
        assertThat(isMatch).isTrue();
    }
}
