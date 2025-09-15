package andreamaiolo.backend.services;

import andreamaiolo.backend.entities.Room;
import andreamaiolo.backend.repositories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;

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
                this.roomRepo.save(newRoom);
            }
        }
    }
}
