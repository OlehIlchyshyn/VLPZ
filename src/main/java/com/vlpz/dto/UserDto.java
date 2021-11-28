package com.vlpz.dto;

import com.vlpz.dto.validation.FieldMatch;
import com.vlpz.dto.validation.UniqueEmail;
import com.vlpz.dto.validation.group.OnRegister;
import com.vlpz.dto.validation.group.OnSignIn;
import com.vlpz.dto.validation.group.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@ApiModel(description = "User data")
@FieldMatch(first = "password", second = "repeatPassword", groups = OnRegister.class)
public class UserDto {

  @JsonProperty(access = READ_ONLY)
  private Long id;

  @NotBlank(groups = OnRegister.class)
  @Null(groups = OnSignIn.class)
  private String name;

  @NotBlank(groups = OnRegister.class)
  @Null(groups = OnSignIn.class)
  private String surname;

  @Null(groups = OnUpdate.class)
  @NotBlank(groups = {OnRegister.class, OnSignIn.class})
  private String password;

  @JsonProperty(access = WRITE_ONLY)
  @Null(groups = {OnUpdate.class, OnSignIn.class})
  @NotBlank(groups = OnRegister.class)
  private String repeatPassword;

  @ApiModelProperty(notes = "Unique user's email")
  @NotBlank(groups = {OnRegister.class, OnSignIn.class})
  @Email
  @UniqueEmail(groups = {OnRegister.class, OnUpdate.class})
  private String email;

  @JsonProperty(access = READ_ONLY)
  @Null(groups = {OnRegister.class, OnUpdate.class, OnSignIn.class})
  private String role;

}
