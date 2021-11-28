package com.vlpz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;


import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@ApiModel(description = "Statistics data")
public class StatisticsDto {
    @JsonProperty(access = READ_ONLY)
    private Long id;

    @JsonProperty(access = READ_ONLY)
    private TaskDto task;

    @JsonProperty(access = READ_ONLY)
    private UserDto user;

    @JsonProperty(access = READ_ONLY)
    private boolean completed;

    @JsonProperty(access = READ_ONLY)
    private int totalAttempts;

    @JsonProperty(access = READ_ONLY)
    private long timeSpentAverageInSeconds;

    @JsonProperty(access = READ_ONLY)
    private long timeSpentOnSuccessfulAttemptInSeconds;

    @JsonProperty(access = READ_ONLY)
    private long timeSpentTotalInSeconds;
}
