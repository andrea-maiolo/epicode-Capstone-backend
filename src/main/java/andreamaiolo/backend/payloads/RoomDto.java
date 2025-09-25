package andreamaiolo.backend.payloads;

import andreamaiolo.backend.entities.Room;

public record RoomDto(
        Long id,
        int number,
        String description,
        Double price,
        int capacity,
        boolean available,
        String picture
) {
    public static RoomDto fromEntity(Room room) {
        return new RoomDto(
                room.getId(),
                room.getNumber(),
                room.getDescription(),
                room.getPrice(),
                room.getCapacity(),
                room.isAvailable(),
                room.getPicture()
        );
    }
}
