package farm.board.service.sign;

import farm.board.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtHandler jwtHandler;

    @Value("${jwt.max-age.access}") // Access 토큰의 만료시간을 지정하는데 사용됩니다.
    private long accessTokenMaxAgeSeconds;

    @Value("${jwt.max-age.refresh}") // Refresh 토큰의 만료시간을 지정하는데 사용됩니다.
    private long refreshTokenMaxAgeSeconds;

    @Value("${jwt.key.access}") // Access 토큰을 생성할 때 사용되는 비밀키를 지정하는데 사용됩니다.
    private String accessKey;

    @Value("${jwt.key.refresh}") // Refresh 토큰을 생성할 때 사용되는 비밀키를 지정하는데 사용됩니다.
    private String refreshKey;

    public String createAccessToken(String subject) {
        return jwtHandler.generateJwtToken(accessKey, subject, accessTokenMaxAgeSeconds);
    }

    public String createRefreshToken(String subject) {
        return jwtHandler.generateJwtToken(refreshKey, subject, refreshTokenMaxAgeSeconds);
    }

    public boolean validateAccessToken(String token) {
        return jwtHandler.validate(accessKey, token);
    }

    public boolean validateRefreshToken(String token) {
        return jwtHandler.validate(refreshKey, token);
    }

    public String extractAccessTokenSubject(String token) {
        return jwtHandler.extractSubject(accessKey, token);
    }

    public String extractRefreshTokenSubject(String token) {
        return jwtHandler.extractSubject(refreshKey, token);
    }

}