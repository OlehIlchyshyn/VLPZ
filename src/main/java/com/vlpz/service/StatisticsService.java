package com.vlpz.service;

import com.vlpz.dto.StatisticsDto;
import com.vlpz.dto.TaskAnswerDto;
import com.vlpz.dto.TaskDto;
import com.vlpz.dto.UserDto;

import java.util.List;

public interface StatisticsService {
    List<StatisticsDto> getAllStatistics();
    List<StatisticsDto> getStatisticsByUserId(Long userId);
    List<StatisticsDto> getStatisticsByTaskId(Long taskId);
    StatisticsDto logAttempt(TaskDto taskDto, UserDto userDto, boolean comparisonResult, long timeSpentOnAttempt);
    void deleteStatistics(Long id);
}
