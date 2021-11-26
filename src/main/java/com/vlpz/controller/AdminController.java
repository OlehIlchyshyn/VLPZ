package com.vlpz.controller;

import com.vlpz.api.AdminApi;
import com.vlpz.controller.assembler.UserModelAssembler;
import com.vlpz.controller.model.UserModel;
import com.vlpz.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

  private final AdminService adminService;
  private final UserModelAssembler modelAssembler;

  @Override
  public CollectionModel<UserModel> getAllUsers() {
    return modelAssembler.toCollectionModel(adminService.getAllUsers());
  }

}
