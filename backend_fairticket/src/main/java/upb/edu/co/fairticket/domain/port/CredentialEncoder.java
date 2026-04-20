package upb.edu.co.fairticket.domain.port;

public interface CredentialEncoder {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}
