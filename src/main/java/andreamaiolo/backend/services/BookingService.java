package andreamaiolo.backend.services;

import andreamaiolo.backend.repositories.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;
}
