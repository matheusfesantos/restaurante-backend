package com.projeto.projeto_restaurante;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjetoRestauranteApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
				.filename(".env")
				.ignoreIfMissing()
				.load();

		String profile = System.getenv("SPRING_PROFILES_ACTIVE");
		if (profile == null) {
			profile = dotenv.get("SPRING_PROFILES_ACTIVE", "prod");
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		}

		System.setProperty("spring.profiles.active", profile);

		SpringApplication.run(ProjetoRestauranteApplication.class, args);
	}
}
