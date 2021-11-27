package com.vlpz;

import com.vlpz.dto.UserDto;
import com.vlpz.model.enums.Role;
import com.vlpz.service.AdminService;
import com.vlpz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
											  @Value("${spring.auth.admin.email}") String adminEmail,
											  @Value("${spring.auth.admin.password}") String adminPassword,
											  @Value("${spring.auth.student.email}") String studentEmail,
											  @Value("${spring.auth.student.password}") String studentPassword) {
		return args -> {
			UserDto studentDto = new UserDto();
			studentDto.setName("Student");
			studentDto.setEmail(studentEmail);
			studentDto.setPassword(studentPassword);
			authService.signUp(studentDto, Role.ROLE_STUDENT);
			authService.signOut();

			UserDto adminDto = new UserDto();
			adminDto.setName("Admin");
			adminDto.setEmail(adminEmail);
			adminDto.setPassword(adminPassword);
			authService.signUp(adminDto, Role.ROLE_ADMIN);
		};
	}
}
