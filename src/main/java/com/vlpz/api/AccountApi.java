package com.vlpz.api;

import com.vlpz.controller.model.UserModel;
import com.vlpz.dto.StatisticsDto;
import com.vlpz.dto.UserDto;
import com.vlpz.dto.validation.group.OnUpdate;
import com.vlpz.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User account REST API")
@ApiResponses({
    @ApiResponse(code = 404, message = "Not found"),
    @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping("/api/account")
public interface AccountApi {

  @ApiOperation("Get active user API")
  @ApiResponse(code = 200, message = "OK", response = UserModel.class)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  UserModel getUser(@AuthenticationPrincipal User user);

  @ApiOperation("Update user API")
  @ApiResponse(code = 200, message = "OK", response = UserModel.class)
  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  UserModel updateUser(@RequestBody @Validated(OnUpdate.class) UserDto userDto);

  @ApiOperation("Delete user API")
  @ApiResponse(code = 204, message = "No content")
  @DeleteMapping
  ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user);

  @ApiOperation("Get statistics for user API")
  @ApiResponse(code = 200, message = "OK", response = StatisticsDto.class, responseContainer = "List")
  @GetMapping("/statistics")
  @ResponseStatus(HttpStatus.OK)
  List<StatisticsDto> getStatistics(@AuthenticationPrincipal User user);
}
