package upb.edu.co.fairticket.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import upb.edu.co.fairticket.domain.port.CredentialEncoder;

@Component
public class BcryptPasswordHasher implements CredentialEncoder {

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    @Override
    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword,
                           String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
