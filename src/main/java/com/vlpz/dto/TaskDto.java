package com.vlpz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vlpz.dto.validation.group.OnCreate;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@ApiModel(description = "Task data")
public class TaskDto {
    @JsonProperty(access = READ_ONLY)
    private Long id;

    @NotBlank(groups = OnCreate.class)
    private String caption;

    @NotBlank(groups = OnCreate.class)
    private String description;

    @NotBlank(groups = OnCreate.class)
    private String complexity;

    @NotBlank(groups = OnCreate.class)
    @JsonProperty(access = WRITE_ONLY)
    private String solutionDiagramJson;
}
