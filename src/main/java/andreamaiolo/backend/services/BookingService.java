package andreamaiolo.backend.services;

import andreamaiolo.backend.entities.Booking;
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
    private BookingRepo bookingRepo;

    public Booking saveBooking(BookingPayload payload) {
        Booking newBooking = new Booking();
        newBooking.setCheckin(payload.checkin());
        newBooking.setCheckout(payload.checkout());
        newBooking.setRoom(payload.room());
        newBooking.setUser(payload.user());
        this.bookingRepo.save(newBooking);
        return newBooking;
    }

    public Page<Booking> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 5) pageSize = 5;
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
        found.setRoom(payload.room());
        this.bookingRepo.save(found);
        return found;
    }

    public void findAndDelete(Long bookingId) {
        Booking found = this.findById(bookingId);
        this.bookingRepo.delete(found);
    }
}
