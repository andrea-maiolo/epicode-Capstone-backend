package andreamaiolo.backend.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginPayload(
        @NotEmpty(message = "The email address is required")
        @Email(message = "The entered email is not in the right format")
        String email,
        @NotEmpty(message = "The password is required")
        String password
) {
}
