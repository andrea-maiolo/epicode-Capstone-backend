package andreamaiolo.backend.controllers;

import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.payloads.RoomDto;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Page<Room> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "30") int pageSize,
                             @RequestParam(defaultValue = "id") String sortBy) {
        return roomService.findAll(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/available")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public List<RoomDto> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout) {

        List<Room> availableRooms = roomService.findAvailableRooms(checkin, checkout);
        System.out.println(availableRooms);
        List<RoomDto> finalCut = availableRooms.stream()
                .map(RoomDto::fromEntity)
                .collect(Collectors.toList());
        System.out.println(finalCut);
        return finalCut;
    }

    @GetMapping("/{roomId}")
    @PreAuthorize("hasAuthority('USER')")
    public Room getRoomById(@PathVariable Long roomId) {
        return this.roomService.findById(roomId);
    }

    @PatchMapping("/{roomId}/picture")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadPicture(@RequestParam("picture") MultipartFile file, @PathVariable Long roomId) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        return this.roomService.uploadPicture(file, roomId);
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

}
