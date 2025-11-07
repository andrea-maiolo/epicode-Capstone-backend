package andreamaiolo.backend.services;

import andreamaiolo.backend.configurations.GmailMailSender;
import andreamaiolo.backend.entities.Booking;
import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.entities.User;
import andreamaiolo.backend.exceptions.BadRequestException;
import andreamaiolo.backend.exceptions.NotFoundException;
import andreamaiolo.backend.payloads.BookingPayload;
import andreamaiolo.backend.repositories.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookingService {
    @Autowired
    UserService userService;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private RoomService roomService;
    @Autowired
    private GmailMailSender gmailMailSender;

    public Booking saveBooking(BookingPayload payload) {
        Room roomToBook = roomService.findById(payload.roomId());
        if (payload.guests() > roomToBook.getCapacity()) {
            throw new BadRequestException("guests exceed room capacity");
        }
        Booking newBooking = new Booking();
        newBooking.setCheckin(payload.checkin());
        newBooking.setCheckout(payload.checkout());
        newBooking.setGuests(payload.guests());
        newBooking.setRoom(roomToBook);
        User bookingUser = userService.findById(payload.userId());
        newBooking.setUser(bookingUser);
        this.gmailMailSender.sendBookingConfirmation(bookingUser, newBooking);
        this.bookingRepo.save(newBooking);
        return newBooking;
    }


    public List<Booking> findAll() {
        return this.bookingRepo.findAll();
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
