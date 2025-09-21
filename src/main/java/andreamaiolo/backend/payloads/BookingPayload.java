package andreamaiolo.backend.payloads;

import java.time.LocalDate;

public record BookingPayload(
        //  @NotEmpty(message = "cant have an empty value")//change with future or present
        LocalDate checkin,
        //   @NotEmpty(message = "cant have an empty value")//same as abov e
        LocalDate checkout,
        // @NotNull(message = "cant have an empty value")
        Long userId,
        // @NotEmpty(message = "cant have an empty value")
        Long roomId
) {
}
