package andreamaiolo.backend.services;

import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.exceptions.BadRequestException;
import andreamaiolo.backend.exceptions.NotFoundException;
import andreamaiolo.backend.payloads.RoomPayload;
import andreamaiolo.backend.repositories.RoomRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private Cloudinary cloudinary;

    public void importRooms(String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String currentLine;
            bufferedReader.readLine();// salta prima linea
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] values = currentLine.split(",");

                Room newRoom = new Room();
                newRoom.setNumber(Integer.parseInt(values[0]));
                newRoom.setDescription(values[1]);
                newRoom.setCapacity(Integer.parseInt(values[3]));
                newRoom.setPrice(Double.parseDouble(values[2]));
                newRoom.setAvailable(true);
                newRoom.setPicture(values[4]);
                this.roomRepo.save(newRoom);
            }
        }
    }

    public Page<Room> findAll(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.roomRepo.findAll(pageable);
    }

    public Room findById(long id) {
        return this.roomRepo.findById(id).orElseThrow(() -> new NotFoundException("room not found "));
    }

    public void uploadPicture(MultipartFile file, Long roomId) {
        Room found = this.findById(roomId);
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) result.get("secure_url");
            found.setPicture(imageUrl);
            this.roomRepo.save(found);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException("image not saved" + ex.getMessage());
        }
    }

    public Room saveRoom(RoomPayload payload) {
        Room newRoom = new Room();
        newRoom.setAvailable(true);
        newRoom.setPrice(payload.price());
        newRoom.setCapacity(payload.capacity());
        newRoom.setDescription(payload.description());
        newRoom.setNumber(payload.number());
        newRoom.setPicture("https://placehold.co/800x600");
        this.roomRepo.save(newRoom);
        return newRoom;
    }

    public Room findAndUpdate(Long roomId, RoomPayload payload) {
        Room found = this.findById(roomId);
        found.setNumber(payload.number());
        found.setDescription(payload.description());
        found.setCapacity(payload.capacity());
        found.setPrice(payload.price());
        this.roomRepo.save(found);
        return found;
    }

    public void findAndDelete(Long roomId) {
        Room found = this.findById(roomId);
        this.roomRepo.delete(found);
    }

    public List<Room> findAvailableRooms(LocalDate checkin, LocalDate checkout) {
        return this.roomRepo.findAvailableRooms(checkin, checkout);
    }
}
