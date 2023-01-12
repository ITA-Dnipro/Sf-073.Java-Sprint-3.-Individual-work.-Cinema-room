package mihail;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import stage.one.StageOneRun;
import stage.one.models.Room;
import stage.one.services.RoomService;


@SpringBootApplication(scanBasePackages = "stage.one.*")
public class CinemaRoomRestServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(CinemaRoomRestServiceApplication.class, args);
		StageOneRun stageOneRun = new StageOneRun(new Room(9,9));
		stageOneRun.run();
	}
}
