package com.vlpz.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Task answer data")
public class TaskAnswerDto {

    @NotBlank
    private String submittedDiagramJson;

    @NotNull
    private Long timeSpentOnAttemptInSecond;
}
