package andreamaiolo.backend.payloads;

public record UserPayload(
        String name,
        String surname,
        String email,
        String password
) {
}
