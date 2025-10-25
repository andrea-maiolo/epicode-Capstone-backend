package andreamaiolo.backend.controllers;

import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.payloads.RoomPayload;
import andreamaiolo.backend.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Page<Room> getAvailableRooms(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            @RequestParam(required = false) Integer guests) {

        if (checkin == null || checkout == null) {
            return this.roomService.findAll(pageNumber, pageSize, sortBy);
        } else {
            return this.roomService.findAvailableRooms(checkin, checkout, guests, pageNumber, pageSize, sortBy);
        }
    }

    @GetMapping("/forAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Room> findForAdmin() {
        return this.roomService.findForAdmin();
    }

    @GetMapping("/{roomId}")
    @PreAuthorize("hasAuthority('USER')")
    public Room getRoomById(@PathVariable Long roomId) {
        return this.roomService.findById(roomId);
    }

    @PatchMapping("/{roomId}/picture")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void uploadPicture(@RequestParam("picture") MultipartFile file, @PathVariable Long roomId) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        this.roomService.uploadPicture(file, roomId);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Room updateRoomInfo(@PathVariable Long roomId, @Validated @RequestBody RoomPayload payload) {
        return this.roomService.findAndUpdate(roomId, payload);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Room createNewRoom(@Validated @RequestBody RoomPayload payload) {
        return this.roomService.saveRoom(payload);
    }

    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRoom(@PathVariable Long roomId) {
        this.roomService.findAndDelete(roomId);
    }

    @PatchMapping("{roomId}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void changeStatus(@PathVariable Long roomId) {
        this.roomService.changeStatus(roomId);
    }

}
