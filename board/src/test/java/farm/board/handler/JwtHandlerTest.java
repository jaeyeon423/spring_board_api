package farm.board.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class JwtHandlerTest {
    JwtHandler jwtHandler = new JwtHandler();
    @Test
    void extractSubjectTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String subject = "subject";
        String token = jwtHandler.generateJwtToken(encodedKey, subject, 6000L);
        // when
        String extractedSubject = jwtHandler.extractSubject(encodedKey, token);
        // then
        assertThat(extractedSubject).isEqualTo(subject);
    }

    @Test
    void validateTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = jwtHandler.generateJwtToken(encodedKey, "subject", 60000L);
        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);
        // then
        assertThat(isValid).isTrue();
    }

    @Test
    void invalidateByInvalidKeyTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = jwtHandler.generateJwtToken(encodedKey, "subject", 60L);
        // when
        boolean isValid = jwtHandler.validate("invalid", token);
        // then
        assertThat(isValid).isFalse();
    }
    @Test
    void invalidateByExpiredTokenTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = jwtHandler.generateJwtToken(encodedKey, "subject", 0L);
        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);
        // then
        assertThat(isValid).isFalse();
    }
}