package com.example.skuCampusAlarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.skuCampusAlarm.repository")
public class SkuCampusAlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkuCampusAlarmApplication.class, args);
	}

}
