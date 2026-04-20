package upb.edu.co.fairticket.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.AuthResponse;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.LoginRequest;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.RegisterUserRequest;
import upb.edu.co.fairticket.domain.usecase.user.LoginUseCase;
import upb.edu.co.fairticket.domain.usecase.user.RegisterUserUseCase;
import upb.edu.co.fairticket.infrastructure.security.JwtService;

@RestController
@RequestMapping("/api/security")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterUserRequest request) {
        var user = switch (request.role().toUpperCase()) {
            case "ORGANIZER" -> registerUserUseCase.registerOrganizer(
                    request.name(), request.email(), request.password());
            default -> registerUserUseCase.registerBuyer(
                    request.name(), request.email(), request.password());
        };
        String token = jwtService.generateToken(
                user.getId(), user.getEmail().value(),
                user.getRole().name());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(user.getId(), user.getName(),
                        user.getEmail().value(),
                        user.getRole().name(), token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        var user = loginUseCase.execute(
                request.email(), request.password());
        String token = jwtService.generateToken(
                user.getId(), user.getEmail().value(),
                user.getRole().name());
        return ResponseEntity.ok(new AuthResponse(
                user.getId(), user.getName(),
                user.getEmail().value(),
                user.getRole().name(), token));
    }
}
