package andreamaiolo.backend.payloads;

import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.entities.User;

import java.time.LocalDate;

public record BookingPayload(
        LocalDate checkin,
        LocalDate checkout,
        User user,
        Room room
) {
}
