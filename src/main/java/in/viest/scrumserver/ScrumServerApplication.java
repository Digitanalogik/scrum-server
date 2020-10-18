package in.viest.scrumserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ScrumServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrumServerApplication.class, args);
    }

}
