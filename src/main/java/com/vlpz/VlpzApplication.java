package com.vlpz;

import com.vlpz.dto.TaskDto;
import com.vlpz.dto.UserDto;
import com.vlpz.service.AuthService;
import com.vlpz.service.StatisticsService;
import com.vlpz.service.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VlpzApplication {
	public static void main(String[] args) {
		SpringApplication.run(VlpzApplication.class, args);
	}
}
