package com.example.zlv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ZlvApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZlvApplication.class, args);
	}

}
