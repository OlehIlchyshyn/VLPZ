package com.vlpz.controller.assembler;

import com.vlpz.controller.AdminController;
import com.vlpz.controller.AccountController;
import com.vlpz.controller.model.UserModel;
import com.vlpz.dto.UserDto;
import com.vlpz.model.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

  public static final String GET_REL = "get";
  public static final String UPDATE_REL = "update";
  public static final String DELETE_REL = "delete";

  public UserModelAssembler() {
    super(AccountController.class, UserModel.class);
  }

  @Override
  public UserModel toModel(UserDto entity) {
    UserModel userModel = new UserModel(entity);

    Link getLink = linkTo(methodOn(AccountController.class).getUser(new User())).withRel(GET_REL);
    Link updateLink = linkTo(methodOn(AccountController.class).updateUser(null)).withRel(UPDATE_REL);
    Link deleteLink = linkTo(methodOn(AccountController.class).deleteUser(new User()))
        .withRel(DELETE_REL);

    return userModel.add(getLink, updateLink, deleteLink);
  }

  public CollectionModel<UserModel> toCollectionModel(List<UserDto> userDtos) {
    List<UserModel> userModels = userDtos.stream()
        .map(this::toModel)
        .collect(Collectors.toList());

    Link self = linkTo((methodOn(AdminController.class).getAllUsers())).withSelfRel();
    return CollectionModel.of(userModels).add(self);
  }

}
