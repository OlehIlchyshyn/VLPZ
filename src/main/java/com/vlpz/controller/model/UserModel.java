package com.vlpz.controller.model;

import com.vlpz.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends RepresentationModel<UserModel> {

  @JsonUnwrapped
  private UserDto userDto;

}
