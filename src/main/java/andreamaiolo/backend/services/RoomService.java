package andreamaiolo.backend.services;

import andreamaiolo.backend.repositories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;
}
