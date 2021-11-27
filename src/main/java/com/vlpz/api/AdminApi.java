package com.vlpz.api;

import com.vlpz.controller.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Admin utilities REST API")
@ApiResponses({
    @ApiResponse(code = 404, message = "Not found"),
    @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping("/api/admin")
public interface AdminApi {

  @ApiOperation("Get list of all users")
  @ApiResponse(code = 200, message = "OK", response = CollectionModel.class)
  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<UserModel> getAllUsers();

}
