package com.vlpz.service.impl;

import com.vlpz.dto.UserDto;
import com.vlpz.repository.UserRepository;
import com.vlpz.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;

  @Override
  public List<UserDto> getAllUsers() {
    log.info("getAllUsers: method is called");
    List<UserDto> allUsers = userRepository.findAll().stream()
        .map(com.vlpz.service.impl.UserServiceImpl::mapUserToUserDto)
        .collect(Collectors.toList());
    log.info("getAllUsers: {} users found", allUsers.size());
    return allUsers;
  }

}
