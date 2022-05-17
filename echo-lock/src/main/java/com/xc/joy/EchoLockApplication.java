package com.xc.joy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xc.joy.dao")
@EnableScheduling
public class EchoLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(EchoLockApplication.class, args);
	}

}
