package geographicdistanceproject.geodistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class GeodistanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeodistanceApplication.class, args);
    }
}
