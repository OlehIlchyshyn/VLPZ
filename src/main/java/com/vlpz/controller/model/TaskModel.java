package com.vlpz.controller.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.vlpz.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel extends RepresentationModel<TaskModel> {
    @JsonUnwrapped
    private TaskDto taskDto;
}
