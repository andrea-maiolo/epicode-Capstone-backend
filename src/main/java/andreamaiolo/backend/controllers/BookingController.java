package andreamaiolo.backend.controllers;

import andreamaiolo.backend.entities.Booking;
import andreamaiolo.backend.exceptions.ValidationException;
import andreamaiolo.backend.payloads.BookingPayload;
import andreamaiolo.backend.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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
    public Booking saveBooking(@RequestBody @Validated BookingPayload payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getDefaultMessage()));
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList());
        } else {
            return this.bookingService.saveBooking(payload);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Booking> getAll() {
        return bookingService.findAll();
    }

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
    public Booking updateBookingInfo(@PathVariable Long bookingId, @Validated @RequestBody BookingPayload payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getDefaultMessage()));
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList());
        } else {
            return this.bookingService.findAndUpdate(bookingId, payload);
        }
    }

}

