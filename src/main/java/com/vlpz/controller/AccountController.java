package com.vlpz.controller;

import com.vlpz.api.AccountApi;
import com.vlpz.controller.assembler.UserModelAssembler;
import com.vlpz.controller.model.UserModel;
import com.vlpz.dto.StatisticsDto;
import com.vlpz.dto.UserDto;
import com.vlpz.model.User;
import com.vlpz.service.StatisticsService;
import com.vlpz.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.vlpz.service.impl.UserServiceImpl.mapUserToUserDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

  private final UserService userService;
  private final StatisticsService statisticsService;
  private final UserModelAssembler modelAssembler;

  @Override
  public UserModel getUser(User user) {
    log.info("getUser: with id {}", user.getId());
    return modelAssembler.toModel(mapUserToUserDto(user));
  }

  @Override
  public UserModel updateUser(UserDto userDto) {
    log.info("updateUser: input userDto {}", userDto);
    return modelAssembler.toModel(userService.updateUser(userDto));
  }

  @Override
  public ResponseEntity<Void> deleteUser(User user) {
    log.info("deleteUser: with id {}", user.getId());
    userService.deleteUser(user);
    return ResponseEntity.noContent().build();
  }

  @Override
  public List<StatisticsDto> getStatistics(User user) {
    log.info("getStatistics: for user {}", user.getId());
    return statisticsService.getStatisticsByUserId(user.getId());
  }
}
