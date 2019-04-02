package be.vlproject.egcevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("be.vlproject.egcevent")
public class EgcEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgcEventApplication.class, args);
	}

}
