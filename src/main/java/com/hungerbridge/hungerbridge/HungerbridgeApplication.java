package com.hungerbridge.hungerbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HungerbridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HungerbridgeApplication.class, args);
	}

}
