package com.flightsearch.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
			System.setProperty("AMADEUS_API_KEY", dotenv.get("AMADEUS_API_KEY"));
			System.setProperty("AMADEUS_API_SECRET", dotenv.get("AMADEUS_API_SECRET"));
			System.setProperty("AMADEUS_GRANT_TYPE", dotenv.get("AMADEUS_GRANT_TYPE"));
			System.setProperty("AMADEUS_TOKEN_URI", dotenv.get("AMADEUS_TOKEN_URI"));

		SpringApplication.run(ApiApplication.class, args);
	}

}
