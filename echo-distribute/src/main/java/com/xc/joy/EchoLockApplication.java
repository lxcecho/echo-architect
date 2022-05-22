package com.xc.joy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xc.joy.mapper")
@EnableScheduling
//@ImportResource("classpath*:redisson.xml")// 使用 xml 方式引入容器注入
public class EchoLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(EchoLockApplication.class, args);
	}

}
