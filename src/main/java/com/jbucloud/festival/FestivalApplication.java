package com.jbucloud.festival;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FestivalApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("KAKAO_REST_API_KEY", dotenv.get("KAKAO_REST_API_KEY"));
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		System.setProperty("FIREBASE_SERVICE_ACCOUNT_FILE", dotenv.get("FIREBASE_SERVICE_ACCOUNT_FILE"));

		SpringApplication.run(FestivalApplication.class, args);
	}
}
