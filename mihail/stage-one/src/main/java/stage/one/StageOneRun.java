package stage.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import stage.one.models.Room;
import stage.one.services.RoomService;

public class StageOneRun implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StageOneRun.class);

    @Autowired
    private final RoomService roomService;
    private final Room room;

    public StageOneRun(Room room) {
        this.room = room;
        this.roomService = new RoomService();
    }

    @Override
    public void run(String... args) {
        String message = roomService.writeJsonDataToFile(room);
        log.info(message);
    }
}
