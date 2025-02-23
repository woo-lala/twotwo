package com.sparta.twotwo;

import com.sparta.twotwo.ai.config.GeminiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(GeminiConfig.class)
public class TwotwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwotwoApplication.class, args);
	}

}
