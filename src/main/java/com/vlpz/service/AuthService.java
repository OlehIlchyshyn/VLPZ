package com.vlpz.service;

import com.vlpz.dto.UserDto;
import com.vlpz.model.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

  UserDto signIn(UserDto userDto);

  UserDto signUp(UserDto userDto, Role role);

  void signOut();

}
