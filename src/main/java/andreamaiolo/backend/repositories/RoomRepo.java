package andreamaiolo.backend.repositories;

import andreamaiolo.backend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    // This query finds all rooms that are NOT booked for the given date range.
    // It works by first finding all rooms that *are* booked for the given dates
    // and then selecting all rooms whose IDs are not in that list.
    @Query("SELECT r FROM Room r WHERE r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b WHERE " +
            "(b.checkin <= :checkout AND b.checkout >= :checkin))")
    List<Room> findAvailableRooms(LocalDate checkin, LocalDate checkout);
}

