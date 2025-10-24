package andreamaiolo.backend.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingPayload(
        @NotNull(message = "The check-in date is required.")
        @FutureOrPresent(message = "Check-in date must be today or in the future.")
        LocalDate checkin,
        @NotNull(message = "The check-out date is required.")
        @FutureOrPresent(message = "Check-out date must be today or in the future.")
        LocalDate checkout,
        // @NotNull(message = "cant have an empty value")
        @NotNull(message = "The user ID is required.")
        Long userId,
        // @NotEmpty(message = "cant have an empty value")
        @NotNull(message = "The room ID is required.")
        Long roomId,
        @Min(value = 1, message = "at least one guest must book")
        int guests
) {
}