package upb.edu.co.fairticket.domain.usecase.user;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.CredentialEncoder;
import upb.edu.co.fairticket.domain.port.UserRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final CredentialEncoder credentialEncoder;

    public User registerBuyer(String name, String emailStr, String rawPassword) {
        Email email = new Email(emailStr);
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DomainException("Email already registered: " + emailStr);
        }
        String hash = credentialEncoder.hash(rawPassword);
        User user = User.createBuyer(name, email, hash);
        return userRepository.save(user);
    }
    
    public User registerOrganizer(String name, String emailStr, String rawPassword) {
        Email email = new Email(emailStr);
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DomainException("Email already registered: " + emailStr);
        }
        String hash = credentialEncoder.hash(rawPassword);
        User user = User.createOrganizer(name, email, hash);
        return userRepository.save(user);
    }
}
