package upb.edu.co.fairticket.domain.model;

import org.junit.jupiter.api.Test;
import upb.edu.co.fairticket.domain.model.enums.Role;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final String HASHED_PASSWORD = "hashedPassword123";

    @Test
    void testCreateMultipleBuyers() {
        User shakira = User.createBuyer("Shakira", new Email("shakira@barranquilla.com"), HASHED_PASSWORD);
        User karolG = User.createBuyer("Karol G", new Email("bichota@medellin.com"), HASHED_PASSWORD);

        assertNotNull(shakira.getId());
        assertEquals("Shakira", shakira.getName());
        assertEquals("shakira@barranquilla.com", shakira.getEmail().value());
        assertTrue(shakira.isBuyer());

        assertNotNull(karolG.getId());
        assertEquals("Karol G", karolG.getName());
        assertEquals("bichota@medellin.com", karolG.getEmail().value());
        assertTrue(karolG.isBuyer());
    }

    @Test
    void testCreateOrganizer() {
        Email email = new Email("juanes@medellin.com");
        User juanes = User.createOrganizer("Juanes", email, HASHED_PASSWORD);
        assertEquals(Role.ORGANIZER, juanes.getRole());
        assertTrue(juanes.isOrganizer());
        assertEquals("Juanes", juanes.getName());
    }

    @Test
    void testCreateAdmin() {
        Email email = new Email("sofia.vergara@toto.com");
        User sofia = User.createAdmin("Sofía Vergara", email, HASHED_PASSWORD);
        assertEquals(Role.ADMIN, sofia.getRole());
        assertTrue(sofia.isAdmin());
        assertEquals("Sofía Vergara", sofia.getName());
    }

    @Test
    void testUpdateProfile() throws InterruptedException {
        Email oldEmail = new Email("rigoberto.uran@etixx.com");
        User rigo = User.createBuyer("Rigoberto Urán", oldEmail, HASHED_PASSWORD);

        Thread.sleep(10);

        Email newEmail = new Email("rigo@gorigogo.com");
        rigo.updateProfile("Rigo Urán", newEmail);

        assertEquals("Rigo Urán", rigo.getName());
        assertEquals(newEmail, rigo.getEmail());
        assertTrue(rigo.getUpdatedAt().isAfter(rigo.getCreatedAt()));
    }
}
