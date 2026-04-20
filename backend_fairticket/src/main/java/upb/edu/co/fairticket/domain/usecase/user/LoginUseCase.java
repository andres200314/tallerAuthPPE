package upb.edu.co.fairticket.domain.usecase.user;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.exception.UserNotFoundException;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.CredentialEncoder;
import upb.edu.co.fairticket.domain.port.UserRepository;

@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository userRepository;
    private final CredentialEncoder credentialEncoder;

    public User execute(String emailStr, String rawPassword) {
        Email email = new Email(emailStr);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found: " + emailStr));

        if (!credentialEncoder.matches(rawPassword,
                user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return user;
    }
}
