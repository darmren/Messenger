package messenger.userservice.jwt;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import messenger.userservice.dto.TokenResponse;
import messenger.userservice.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

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

    public String generateAccessToken(String username) {
        return buildToken(username, jwtProperties.getAccessLifetime(), jwtProperties.getAccessSecret());
    }

    public String generateRefreshToken(String username) {
        var refresh = buildToken(username, jwtProperties.getRefreshLifetime(), jwtProperties.getRefreshSecret());
        return buildToken(username, jwtProperties.getRefreshLifetime(), jwtProperties.getRefreshSecret());
    }

    private String buildToken(String username, long expiration, String secret) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, secret)
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

