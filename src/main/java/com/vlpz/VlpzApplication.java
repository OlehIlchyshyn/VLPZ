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

	@Bean
	public CommandLineRunner demoAdminAndStudent(AuthService authService,
											  TaskService taskService,
											  StatisticsService statisticsService,
											  @Value("${spring.auth.admin.email}") String adminEmail,
											  @Value("${spring.auth.admin.password}") String adminPassword,
											  @Value("${spring.auth.student.email}") String studentEmail,
											  @Value("${spring.auth.student.password}") String studentPassword) {
		return args -> {
			UserDto studentDto = new UserDto();
			studentDto.setName("Student");
			studentDto.setEmail(studentEmail);
			studentDto.setPassword(studentPassword);
			studentDto = authService.signUp(studentDto);
			authService.signOut();

			UserDto adminDto = new UserDto();
			adminDto.setName("Admin");
			adminDto.setEmail(adminEmail);
			adminDto.setPassword(adminPassword);
			authService.signUp(adminDto);

			TaskDto taskDto = new TaskDto();
			taskDto.setCaption("Example");
			taskDto.setDescription("Test task");
			taskDto.setComplexity("HARD");
			taskDto.setSolutionDiagramJson("json");
			taskDto = taskService.createTask(taskDto);

			statisticsService.logAttempt(taskDto, studentDto, false, 2);
			statisticsService.logAttempt(taskDto, studentDto, true, 35);
		};
	}
}
