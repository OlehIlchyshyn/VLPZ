package com.vlpz.service.impl;

import com.vlpz.dto.UserDto;
import com.vlpz.model.User;
import com.vlpz.model.enums.Role;
import com.vlpz.repository.UserRepository;
import com.vlpz.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public void deleteUser(User user) {
    log.info("deleteUser: about to delete user with email {}", user.getEmail());
    SecurityContextHolder.clearContext();
    userRepository.delete(user);
  }

  @Override
  public boolean isUserExistsWithEmail(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  @Override
  public UserDto updateUser(UserDto userDto) {
    User user = mapUserDtoToUser(userDto);
    log.info("updateUser: about to update user {}", user);
    user = userRepository.save(user);
    return mapUserToUserDto(user);
  }

  public static User mapUserDtoToUser(UserDto userDto) {
    log.debug("mapUserDtoToUser: map to User from UserDto: {}", userDto);
    User user = new User();
    if (userDto.getRole() != null) {
      user.setRole(Role.valueOf(userDto.getRole()));
    }
    BeanUtils.copyProperties(userDto, user);
    return user;
  }

  public static UserDto mapUserToUserDto(User user) {
    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(user, userDto);
    userDto.setPassword(null);
    userDto.setRole(user.getRole().name());
    log.debug("mapUserToUserDto: map from User to UserDto: {}", userDto);
    return userDto;
  }

}
