package andreamaiolo.backend.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPayload(
        @NotBlank(message = "The name is required.")
        @Size(max = 50, message = "Name cannot exceed 50 characters.")
        String name,
        @NotBlank(message = "The surname is required.")
        @Size(max = 50, message = "Surname cannot exceed 50 characters.")
        String surname,
        @NotBlank(message = "The email address is required.")
        @Email(message = "The entered email is not in a valid format.")
        String email,
        @NotBlank(message = "The password is required.")
        @Size(min = 2, max = 128, message = "Password must be between 2 and 128 characters.")
        String password
) {
}
