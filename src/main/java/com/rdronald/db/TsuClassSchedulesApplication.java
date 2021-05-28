package com.rdronald.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TsuClassSchedulesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TsuClassSchedulesApplication.class, args);
	}

}
