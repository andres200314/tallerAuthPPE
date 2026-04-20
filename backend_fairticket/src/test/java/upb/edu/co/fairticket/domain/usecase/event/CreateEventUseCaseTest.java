package upb.edu.co.fairticket.domain.usecase.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.Event;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.enums.EventCategory;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.EventRepository;
import upb.edu.co.fairticket.domain.port.UserRepository;
import upb.edu.co.fairticket.domain.exception.DomainException;
import upb.edu.co.fairticket.domain.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEventUseCaseTest {

    private static final String HASHED_PASSWORD = "hashedPassword123";

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateEventUseCase createEventUseCase;

    private final LocalDateTime date = LocalDateTime.now().plusDays(10);
    private final UUID orgId = UUID.randomUUID();

    @Test
    void testExecuteSuccessOrganizerJCorreal() {
        User organizer = User.createOrganizer("Julio Correal", new Email("julio@correal.com"), HASHED_PASSWORD);
        when(userRepository.findById(orgId)).thenReturn(Optional.of(organizer));
        when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArguments()[0]);

        Event event = createEventUseCase.execute("Rock al Parque", "Desc", "Parque Simón Bolívar", date, date.minusDays(5), date.minusDays(1), 
                EventCategory.MUSICAL, 5, 100, orgId);

        assertNotNull(event);
        assertEquals("Rock al Parque", event.getName());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void testExecuteSuccessAdminBetty() {
        User admin = User.createAdmin("Betty la Fea", new Email("betty@ecomoda.com"), HASHED_PASSWORD);
        when(userRepository.findById(orgId)).thenReturn(Optional.of(admin));
        when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArguments()[0]);

        Event event = createEventUseCase.execute("Desfile Ecomoda", "Lanzamiento Colección", "Hotel Casa Medina", date, date.minusDays(5), date.minusDays(1), 
                EventCategory.CULTURAL, 5, 100, orgId);

        assertNotNull(event);
        assertEquals("Desfile Ecomoda", event.getName());
    }

    @Test
    void testExecuteUnauthorizedBuyer() {
        User buyer = User.createBuyer("Radamel Falcao", new Email("falcao@santamarta.com"), HASHED_PASSWORD);
        when(userRepository.findById(orgId)).thenReturn(Optional.of(buyer));

        assertThrows(DomainException.class, () -> createEventUseCase.execute("Ev", "Desc", "Ven", date, date, date, 
                EventCategory.MUSICAL, 5, 100, orgId));
        verify(eventRepository, never()).save(any());
    }

    @Test
    void testExecuteOrganizerNotFound() {
        when(userRepository.findById(orgId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> createEventUseCase.execute("Ev", "Desc", "Ven", date, date, date, 
                EventCategory.MUSICAL, 5, 100, orgId));
    }
}
