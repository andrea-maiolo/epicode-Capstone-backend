package andreamaiolo.backend.services;

import andreamaiolo.backend.entities.Booking;
import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.entities.User;
import andreamaiolo.backend.exceptions.NotFoundException;
import andreamaiolo.backend.payloads.BookingPayload;
import andreamaiolo.backend.repositories.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookingService {
    @Autowired
    UserService userService;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private RoomService roomService;

    public Booking saveBooking(BookingPayload payload) {
        System.out.println(payload);
        Booking newBooking = new Booking();
        newBooking.setCheckin(payload.checkin());
        newBooking.setCheckout(payload.checkout());
        Room roomToBook = roomService.findById(payload.roomId());
        newBooking.setRoom(roomToBook);
        User bookingUser = userService.findById(payload.userId());
        newBooking.setUser(bookingUser);
        this.bookingRepo.save(newBooking);
        return newBooking;
    }

    public Page<Booking> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 30) pageSize = 30;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.bookingRepo.findAll(pageable);
    }

    public Booking findById(Long bookingId) {
        return this.bookingRepo.findById(bookingId).orElseThrow(() -> new NotFoundException("this booking does not exist"));
    }

    public Booking findAndUpdate(Long bookingId, BookingPayload payload) {
        Booking found = this.findById(bookingId);
        found.setCheckin(payload.checkin());
        found.setCheckout(payload.checkout());
        Room roomUpdate = roomService.findById(payload.roomId());
        found.setRoom(roomUpdate);
        this.bookingRepo.save(found);
        return found;
    }

    public void findAndDelete(Long bookingId) {
        Booking found = this.findById(bookingId);
        this.bookingRepo.delete(found);
    }
}
