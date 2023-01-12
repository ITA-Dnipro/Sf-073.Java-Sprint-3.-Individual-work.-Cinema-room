package mihail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaRoomRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaRoomRestServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RoomService roomService){
		return args -> {
			String message = roomService.writeJsonDataToFile(new Room(9, 9));
			log.info(message);
		};
	}

}
