package group.oneonetwo.hotelintelligencesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 文
 */
@EnableScheduling
@SpringBootApplication
public class HotelIntelligenceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelIntelligenceSystemApplication.class, args);
    }

}
