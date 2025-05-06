package messenger.userservice.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import messenger.userservice.dto.LoginRequest;
import messenger.userservice.dto.RefreshRequest;
import messenger.userservice.dto.RegisterRequest;
import messenger.userservice.dto.TokenResponse;
import messenger.userservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authManager;

    @PostMapping("/register")
    public TokenResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request.username(), request.password());
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
//        Authentication auth = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.username(), request.password()
//                )
//        );
        return authService.login(request.username(), request.password());
    }

    @PostMapping("/refresh")
    public TokenResponse refreshSession(@RequestBody RefreshRequest request) throws IllegalAccessException {
        return authService.refreshSession(request.token());
    }
}

