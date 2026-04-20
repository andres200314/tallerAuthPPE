package upb.edu.co.fairticket.domain.usecase.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.CredentialEncoder;
import upb.edu.co.fairticket.domain.port.UserRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CredentialEncoder credentialEncoder;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    private static final String RAW_PASSWORD = "password123";
    private static final String HASHED_PASSWORD = "hashedPassword123";

    @ParameterizedTest
    @CsvSource({
            "Carlos Vives, carlos.vives@santamarta.com",
            "Shakira, shakira@barranquilla.com",
            "Maluma, maluma@medellin.com"
    })
    void testRegisterMultipleBuyersSuccess(String name, String emailStr) {
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());
        when(credentialEncoder.hash(RAW_PASSWORD)).thenReturn(HASHED_PASSWORD);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User result = registerUserUseCase.registerBuyer(name, emailStr, RAW_PASSWORD);

        assertNotNull(result);
        assertTrue(result.isBuyer());
        assertEquals(name, result.getName());
        assertEquals(emailStr, result.getEmail().value());
        verify(userRepository).save(any(User.class));
    }

    @ParameterizedTest
    @CsvSource({
            "J Balvin, jose@vibras.com",
            "Julio Correal, julio@rockalparque.com"
    })
    void testRegisterMultipleOrganizersSuccess(String name, String emailStr) {
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());
        when(credentialEncoder.hash(RAW_PASSWORD)).thenReturn(HASHED_PASSWORD);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User result = registerUserUseCase.registerOrganizer(name, emailStr, RAW_PASSWORD);

        assertNotNull(result);
        assertTrue(result.isOrganizer());
        assertEquals(name, result.getName());
    }

    @Test
    void testRegisterBuyerDuplicateEmailThrowsException() {
        when(userRepository.findByEmail(any(Email.class)))
                .thenReturn(Optional.of(User.createBuyer("Existing", new Email("falcao@santamarta.com"), HASHED_PASSWORD)));

        DomainException exception = assertThrows(DomainException.class,
                () -> registerUserUseCase.registerBuyer("Radamel Falcao", "falcao@santamarta.com", RAW_PASSWORD));

        assertTrue(exception.getMessage().contains("Email already registered"));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterOrganizerDuplicateEmailThrowsException() {
        when(userRepository.findByEmail(any(Email.class)))
                .thenReturn(Optional.of(User.createBuyer("Existing", new Email("goyo@chocquibtown.com"), HASHED_PASSWORD)));

        assertThrows(DomainException.class,
                () -> registerUserUseCase.registerOrganizer("Goyo", "goyo@chocquibtown.com", RAW_PASSWORD));
    }
}
