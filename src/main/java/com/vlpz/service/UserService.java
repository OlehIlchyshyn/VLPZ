package com.vlpz.service;

import com.vlpz.dto.UserDto;
import com.vlpz.model.User;

public interface UserService {

  UserDto updateUser(UserDto userDto);

  void deleteUser(User user);

  boolean isUserExistsWithEmail(String email);

}
