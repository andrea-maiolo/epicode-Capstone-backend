package andreamaiolo.backend.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomPayload(
        @NotNull(message = "the room must have a number")
        int number,
        @NotBlank(message = "The description cannot be empty.")
        @Size(min = 5, max = 255, message = "Description must be between 10 and 255 characters.")
        String description,
        @NotNull(message = "The price is required.")
        @Min(value = 1, message = "Price must be at least 1.")
        double price,
        @NotNull(message = "The capacity is required.")
        @Min(value = 1, message = "Capacity must be at least 1 person.")
        int capacity
) {
}