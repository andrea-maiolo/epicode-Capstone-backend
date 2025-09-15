package andreamaiolo.backend.controllers;

import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public Page<Room> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "15") int pageSize,
                             @RequestParam(defaultValue = "id") String sortBy) {
        return roomService.findAll(pageNumber, pageSize, sortBy);
    }
}
