package engtelecom.bcd.projetoBcd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ProjetoBcdApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProjetoBcdApplication.class, args);
		log.info("Aplicação finalizada");
	}

}
