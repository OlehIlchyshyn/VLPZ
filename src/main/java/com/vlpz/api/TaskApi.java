package com.vlpz.api;

import com.vlpz.controller.model.TaskModel;
import com.vlpz.dto.TaskAnswerDto;
import com.vlpz.dto.TaskDto;
import com.vlpz.dto.validation.group.OnCreate;
import com.vlpz.dto.validation.group.OnUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Task management REST API")
@ApiResponses({
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping("/api/task")
public interface TaskApi {

    @ApiOperation("Get list of all tasks")
    @ApiResponse(code = 200, message = "OK", response = CollectionModel.class)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    CollectionModel<TaskModel> getAllTasks();

    @ApiOperation("Get task details API")
    @ApiResponse(code = 200, message = "OK", response = TaskModel.class)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskModel getTask(@PathVariable Long id);

    @ApiOperation("Create new task API")
    @ApiResponse(code = 200, message = "OK", response = TaskModel.class)
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    TaskModel createTask(@RequestBody @Validated(OnCreate.class) TaskDto taskDto);

    @ApiOperation("Update task API")
    @ApiResponse(code = 200, message = "OK", response = TaskModel.class)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskModel updateTask(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) TaskDto taskDto);

    @ApiOperation("Delete task API")
    @ApiResponse(code = 204, message = "No content")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable Long id);

    @ApiOperation("Submit task result API")
    @ApiResponse(code = 200, message = "OK", response = Boolean.class)
    @PostMapping("/{id}")
    boolean submitResult(@PathVariable Long id, @RequestBody @Validated TaskAnswerDto taskAnswerDto);
}
