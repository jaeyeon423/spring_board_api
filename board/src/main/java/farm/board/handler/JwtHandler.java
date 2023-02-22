package farm.board.handler;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtHandler {

    private String type = "Bearer ";

    public String generateJwtToken(String encodedKey, String subject, long maxAgeSeconds) {
        return type + Jwts.builder()
                .setSubject(subject) // JWT에 대한 주제를 설정
                .setIssuedAt(new Date()) //JWT의 발급 시간을 설정
                .setExpiration(new Date(System.currentTimeMillis() + maxAgeSeconds)) // JWT의 만료 시간을 설정
                .signWith(SignatureAlgorithm.HS512, encodedKey) // JWT에 서명할 알고리즘과 비밀 키를 설정
                .compact();
    }

    public String extractSubject(String encodedKey, String token){
        token = token.replaceAll("Bearer ","");
        return Jwts
                .parser()
                .setSigningKey(encodedKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String encodedKey, String token) {
        try {
            parse(encodedKey, token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parse(String key, String token) {
        token = token.replaceAll("Bearer ","");
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
    }
}
