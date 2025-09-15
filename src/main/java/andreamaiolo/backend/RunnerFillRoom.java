package andreamaiolo.backend;

import andreamaiolo.backend.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

//@Component
public class RunnerFillRoom implements CommandLineRunner {
    @Autowired
    private RoomService roomService;


    @Override
    public void run(String... args) throws Exception {
        String roomsPath = "src/main/resources/rooms_en.csv";

        try {
            this.roomService.importRooms(roomsPath);
            System.out.println("done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
