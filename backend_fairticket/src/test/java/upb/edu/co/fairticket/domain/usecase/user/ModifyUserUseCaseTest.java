package upb.edu.co.fairticket.domain.usecase.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.UserRepository;
import upb.edu.co.fairticket.domain.exception.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModifyUserUseCaseTest {
    private static final String HASHED_PASSWORD = "hashedPassword123";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ModifyUserUseCase modifyUserUseCase;

    @Test
    void testExecuteSuccess() {
        UUID id = UUID.randomUUID();
        User rigo = User.createBuyer("Rigoberto Urán", new Email("rigo@urrao.com"), HASHED_PASSWORD);
        when(userRepository.findById(id)).thenReturn(Optional.of(rigo));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User result = modifyUserUseCase.execute(id, "Rigo Urán", "rigo@gorigogo.com");

        assertEquals("Rigo Urán", result.getName());
        assertEquals("rigo@gorigogo.com", result.getEmail().value());
        verify(userRepository).save(rigo);
    }

    @Test
    void testExecuteNotFoundThrowsException() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> modifyUserUseCase.execute(id, "Betty", "betty@ecomoda.com"));
    }
}
