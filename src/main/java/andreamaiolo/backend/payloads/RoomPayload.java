package andreamaiolo.backend.payloads;

public record RoomPayload(
        int number,
        String description,
        double price,
        int capacity
) {
}
