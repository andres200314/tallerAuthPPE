package upb.edu.co.fairticket.adapter.in.rest.dto.request;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String name,
        String email,
        String role,
        String token
) {}
