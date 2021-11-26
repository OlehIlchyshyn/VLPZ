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

//	@Autowired public static AdminService adminService;
	public static void main(String[] args) {
		SpringApplication.run(VlpzApplication.class, args);
//		adminService.getAllUsers();
	}

	@Bean
	public CommandLineRunner demoAdmin(AuthService authService,
									   @Value("${spring.auth.admin.password}") String password,
									   @Value("${spring.auth.admin.email}") String email) {
		return args -> {
			UserDto userDto = new UserDto();
			userDto.setName("Admin");
			userDto.setEmail(email);
			userDto.setPassword(password);
			authService.signUp(userDto, Role.ROLE_ADMIN);
		};
	}
}
