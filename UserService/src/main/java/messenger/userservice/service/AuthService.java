package messenger.userservice.service;

import lombok.RequiredArgsConstructor;
import messenger.userservice.dto.TokenResponse;
import messenger.userservice.jwt.JwtProperties;
import messenger.userservice.jwt.JwtProvider;
import messenger.userservice.model.RefreshToken;
import messenger.userservice.model.User;
import messenger.userservice.repository.RefreshTokenRepository;
import messenger.userservice.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;
    //TODO переделать через Spring Security
    public final UserDetailsServiceImpl userDetailsService;

    public TokenResponse register(String username, String password) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new RuntimeException("User already exists");
        });
        User user = User.builder()
                .username(username)
                .passwordHash(passwordEncoder.encode(password))
                .build();
        userRepository.save(user);
        return generateTokens(user);
    }

    public TokenResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid password");
        }
        return generateTokens(user);
    }

    public TokenResponse refreshSession(RefreshToken refreshToken) throws IllegalAccessException {
        refreshTokenRepository.findByToken(refreshToken.getToken())//ищем токен-сессию
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));
        if (refreshToken.getExpiredAt().before(new Date())) { //если токен истек, удаляем сессию
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalAccessException("Refresh token expired");
        }
        //обновляем пару токенов
        User user = refreshToken.getUser();
        return generateTokens(user);
    }

    public TokenResponse generateTokens(User user) {
        String access = jwtProvider.generateAccessToken(user.getId());
        String refresh = jwtProvider.generateRefreshToken(user.getId());
        var now = new Date();
        RefreshToken refreshToken = RefreshToken.builder()
                .createdAt(now)
                .expiredAt(new Date(now.getTime() + jwtProperties.getAccessLifetime()))
                .token(refresh)
                .user(user)
                .build();
        refreshTokenRepository.save(refreshToken);
        return new TokenResponse(access, refresh);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

