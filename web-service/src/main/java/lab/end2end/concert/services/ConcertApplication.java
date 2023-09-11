package lab.end2end.concert.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "lab.end2end.concert.domain")
// TODO: add annotation to scan for entities
public class ConcertApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConcertApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO: make sure the port of web front is correct.
				// registry.addMapping("/concerts").allowedOrigins("http://localhost:8080");
				registry.addMapping("/**");
			}
		};
	}
}