package andreamaiolo.backend.controllers;

import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{roomId}")
    public Room getRoomById(@PathVariable Long roomId) {
        return this.roomService.findById(roomId);
    }

    @PatchMapping("/{roomId}/picture")
    public String uploadPicture(@RequestParam("picture") MultipartFile file, @PathVariable Long roomId) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        return this.roomService.uploadPicture(file, roomId);
    }
}
