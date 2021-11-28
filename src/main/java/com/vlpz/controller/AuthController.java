package com.vlpz.controller;

import com.vlpz.api.AuthApi;
import com.vlpz.controller.assembler.UserModelAssembler;
import com.vlpz.controller.model.UserModel;
import com.vlpz.dto.UserDto;
import com.vlpz.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

  private final AuthService authService;
  private final UserModelAssembler modelAssembler;

  @Override
  public UserModel signIn(UserDto userDto) {
    return modelAssembler.toModel(authService.signIn(userDto));
  }

  @Override
  public UserModel signUp(UserDto userDto) {
    return modelAssembler.toModel(authService.signUp(userDto));
  }

  @Override
  public ResponseEntity<Void> signOut() {
    authService.signOut();
    return ResponseEntity.noContent().build();
  }

}
