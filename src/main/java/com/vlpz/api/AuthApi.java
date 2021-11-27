package com.vlpz.api;

import com.vlpz.controller.model.UserModel;
import com.vlpz.dto.UserDto;
import com.vlpz.dto.validation.group.OnRegister;
import com.vlpz.dto.validation.group.OnSignIn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Auth management REST API")
@ApiResponses({
    @ApiResponse(code = 404, message = "Not found"),
    @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping("/api/auth")
public interface AuthApi {

  @ApiOperation("Sign in user to the system")
  @ApiResponse(code = 200, message = "OK", response = UserModel.class)
  @PostMapping("/signin")
  @ResponseStatus(HttpStatus.OK)
  UserModel signIn(@RequestBody @Validated(OnSignIn.class) UserDto userDto);

  @ApiOperation("Sign up and automatically sign in user to the system")
  @ApiResponse(code = 201, message = "Created", response = UserModel.class)
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  UserModel signUp(@RequestBody @Validated(OnRegister.class) UserDto userDto);

  @ApiOperation("Sign out current user from the system")
  @ApiResponse(code = 204, message = "No content")
  @GetMapping("/signout")
  ResponseEntity<Void> signOut();

}
