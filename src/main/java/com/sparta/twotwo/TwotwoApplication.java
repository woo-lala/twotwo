package com.sparta.twotwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TwotwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwotwoApplication.class, args);
	}

}
