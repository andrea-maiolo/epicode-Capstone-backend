package andreamaiolo.backend.controllers;

import andreamaiolo.backend.entities.Booking;
import andreamaiolo.backend.payloads.BookingPayload;
import andreamaiolo.backend.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public Booking saveBooking(@RequestBody @Validated BookingPayload payload) {
        return this.bookingService.saveBooking(payload);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Booking> getAll() {
        return bookingService.findAll();
    }
//    public Page<Booking> getAll(@RequestParam(defaultValue = "0") int pageNumber,
//                                @RequestParam(defaultValue = "30") int pageSize,
//                                @RequestParam(defaultValue = "id") String sortBy) {
//        return bookingService.findAll(pageNumber, pageSize, sortBy);
//    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('USER')  or hasAuthority('ADMIN')")
    public Booking getBookingById(@PathVariable Long bookingId) {
        return this.bookingService.findById(bookingId);
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteBooking(@PathVariable Long bookingId) {
        this.bookingService.findAndDelete(bookingId);
    }

    @PatchMapping("/update/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Booking updateBookingInfo(@PathVariable Long bookingId, @Validated @RequestBody BookingPayload payload) {
        return this.bookingService.findAndUpdate(bookingId, payload);
    }

}

