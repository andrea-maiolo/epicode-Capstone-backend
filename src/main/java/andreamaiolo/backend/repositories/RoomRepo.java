package andreamaiolo.backend.repositories;

import andreamaiolo.backend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.available = true AND r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b WHERE " +
            "(b.checkin <= :checkout AND b.checkout >= :checkin))")
    List<Room> findAvailableRooms(LocalDate checkin, LocalDate checkout);
}
