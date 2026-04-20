package upb.edu.co.fairticket.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import upb.edu.co.fairticket.domain.port.*;
import upb.edu.co.fairticket.domain.usecase.event.CreateEventUseCase;
import upb.edu.co.fairticket.domain.usecase.event.DeleteEventUseCase;
import upb.edu.co.fairticket.domain.usecase.event.EditEventUseCase;
import upb.edu.co.fairticket.domain.usecase.purchase.CancelPurchaseUseCase;
import upb.edu.co.fairticket.domain.usecase.purchase.CreatePurchaseUseCase;
import upb.edu.co.fairticket.domain.usecase.purchase.GetPurchaseUseCase;
import upb.edu.co.fairticket.domain.usecase.ticket.BuyTicketUseCase;
import upb.edu.co.fairticket.domain.usecase.ticket.CancelTicketUseCase;
import upb.edu.co.fairticket.domain.usecase.ticket.GetTicketsUseCase;
import upb.edu.co.fairticket.domain.usecase.user.*;

@Configuration
public class BeanConfig {

    // User Use Cases
    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            CredentialEncoder credentialEncoder) {
        return new RegisterUserUseCase(
                userRepository, credentialEncoder);
    }

    @Bean
    public LoginUseCase loginUseCase(
            UserRepository userRepository,
            CredentialEncoder credentialEncoder) {
        return new LoginUseCase(
                userRepository, credentialEncoder);
    }

    @Bean
    public ListUserUseCase listUserUseCase(UserRepository userRepository) {
        return new ListUserUseCase(userRepository);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCase(userRepository);
    }

    @Bean
    public ModifyUserUseCase modifyUserUseCase(UserRepository userRepository) {
        return new ModifyUserUseCase(userRepository);
    }

    // Event Use Cases
    @Bean
    public CreateEventUseCase createEventUseCase(EventRepository eventRepository, UserRepository userRepository) {
        return new CreateEventUseCase(eventRepository, userRepository);
    }

    @Bean
    public EditEventUseCase editEventUseCase(EventRepository eventRepository) {
        return new EditEventUseCase(eventRepository);
    }

    @Bean
    public DeleteEventUseCase deleteEventUseCase(EventRepository eventRepository) {
        return new DeleteEventUseCase(eventRepository);
    }

    // Ticket Use Cases
    @Bean
    public BuyTicketUseCase buyTicketUseCase(TicketRepository ticketRepository, EventRepository eventRepository) {
        return new BuyTicketUseCase(ticketRepository, eventRepository);
    }

    @Bean
    public CancelTicketUseCase cancelTicketUseCase(TicketRepository ticketRepository) {
        return new CancelTicketUseCase(ticketRepository);
    }

    @Bean
    public GetTicketsUseCase getTicketsUseCase(TicketRepository ticketRepository) {
        return new GetTicketsUseCase(ticketRepository);
    }

    // Purchase Use Cases
    @Bean
    public CreatePurchaseUseCase createPurchaseUseCase(PurchaseRepository purchaseRepository, TicketRepository ticketRepository) {
        return new CreatePurchaseUseCase(purchaseRepository, ticketRepository);
    }

    @Bean
    public CancelPurchaseUseCase cancelPurchaseUseCase(PurchaseRepository purchaseRepository, TicketRepository ticketRepository) {
        return new CancelPurchaseUseCase(purchaseRepository, ticketRepository);
    }

    @Bean
    public GetPurchaseUseCase getPurchaseUseCase(PurchaseRepository purchaseRepository) {
        return new GetPurchaseUseCase(purchaseRepository);
    }
}
