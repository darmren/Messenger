package messenger.userservice.jwt;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import messenger.userservice.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {
    //TODO засунуть в пропертис
//    private final String jwtSecret = "accessSecret";
//    private final String refreshSecret = "refreshSecret";
//    private final long accessExpirationMs = 15 * 60 * 1000; // 15 мин
//    private final long refreshExpirationMs = 7 * 24 * 60 * 60 * 1000; // 7 дней
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessToken(Long userId) {
        return buildToken(userId, jwtProperties.getAccessLifetime(), jwtProperties.getAccessSecret());
    }

    public String generateRefreshToken(Long userId) {
        var refresh = buildToken(userId, jwtProperties.getRefreshLifetime(), jwtProperties.getRefreshSecret());
        return buildToken(userId, jwtProperties.getRefreshLifetime(), jwtProperties.getRefreshSecret());
    }



    private String buildToken(Long userId, long expiration, String secret) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String getUsernameFromAccessToken(String token) {
        return parseToken(token, jwtProperties.getAccessSecret());
    }

    public String getUsernameFromRefreshToken(String token) {
        return parseToken(token, jwtProperties.getRefreshSecret());
    }

    private String parseToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateAccessToken(String token) {
        return validate(token, jwtProperties.getAccessSecret());
    }

    public boolean validateRefreshToken(String token) {
        return validate(token, jwtProperties.getRefreshSecret());
    }

    private boolean validate(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}

