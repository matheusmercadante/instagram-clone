package com.clone.instagram.mspost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MsPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPostApplication.class, args);
	}

}
