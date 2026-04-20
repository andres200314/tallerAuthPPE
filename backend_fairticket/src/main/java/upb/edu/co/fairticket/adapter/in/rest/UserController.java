package upb.edu.co.fairticket.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.RegisterUserRequest;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.ModifyUserRequest;
import upb.edu.co.fairticket.adapter.in.rest.dto.response.UserResponse;
import upb.edu.co.fairticket.domain.usecase.user.RegisterUserUseCase;
import upb.edu.co.fairticket.domain.usecase.user.ListUserUseCase;
import upb.edu.co.fairticket.domain.usecase.user.DeleteUserUseCase;
import upb.edu.co.fairticket.domain.usecase.user.ModifyUserUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ListUserUseCase listUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ModifyUserUseCase modifyUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest request) {
        var user = switch (request.role().toUpperCase()) {
            case "ORGANIZER" -> registerUserUseCase.registerOrganizer(request.name(), request.email(), request.password());
            default -> registerUserUseCase.registerBuyer(request.name(), request.email(), request.password());
        };
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> list() {
        return ResponseEntity.ok(listUserUseCase.execute().stream().map(UserResponse::from).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> modify(@PathVariable UUID id, @RequestBody ModifyUserRequest request) {
        var user = modifyUserUseCase.execute(id, request.name(), request.email());
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
