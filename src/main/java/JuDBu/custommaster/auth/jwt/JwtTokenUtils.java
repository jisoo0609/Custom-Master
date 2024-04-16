package JuDBu.custommaster.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtils {
    // JWT를 만드는 용도의 암호키
    private final Key accessSigningKey;
    private final Key refreshSigningKey;
    // JWT를 해석하는 용도의 객체
    private JwtParser jwtParser;

    public JwtTokenUtils(
            @Value("${jwt.secret}")
            String jwtAccessSecret,
            @Value("${jwt.refresh-secret}")
            String jwtRefreshSecret
    ) {
        this.accessSigningKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.refreshSigningKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    // UserDetails를 받아 JWT로 변환
    public String generateToken(UserDetails userDetails) {
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(accessSigningKey)
                .build();
        // 호출되었을때 time
        Instant now = Instant.now();
        Claims jwtClaims = Jwts.claims()
                // 사용자 이름
                .setSubject(userDetails.getUsername())
                // 발급 시간
                .setIssuedAt(Date.from(now))
                // 원할한 테스트를 위해 발급된 토큰 7일 지나면 만료되도록 설정해놓음
                // 로그인 한 후 한시간이 지나면 토큰이 만료됨
                .setExpiration(Date.from(now.plusSeconds((60 * 60 * 24 * 7))));

        return Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(accessSigningKey)
                .compact();
    }
    public String generateRefreshToken(UserDetails userDetails) {
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(refreshSigningKey)
                .build();
        // 호출되었을때 time
        Instant now = Instant.now();
        Claims jwtClaims = Jwts.claims()
                // 사용자 이름
                .setSubject(userDetails.getUsername())
                // 발급 시간
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds((60 * 60 * 24 * 7))));

        return Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(refreshSigningKey)
                .compact();
    }

    // 정상적인 JWT인지를 판단하는 메서드
    public boolean validate(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("invalid jwt");
        }
        return false;
    }

    // 실제 데이터(Payload)를 반환하는 메서드
    public Claims parseClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }
}
