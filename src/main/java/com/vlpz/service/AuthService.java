package com.vlpz.service;

import com.vlpz.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

  UserDto signIn(UserDto userDto);

  UserDto signUp(UserDto userDto);

  void signOut();

}
