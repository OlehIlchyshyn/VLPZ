package com.vlpz.service.impl;

import com.vlpz.dto.StatisticsDto;
import com.vlpz.dto.TaskDto;
import com.vlpz.dto.UserDto;
import com.vlpz.model.Statistics;
import com.vlpz.repository.StatisticsRepository;
import com.vlpz.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.vlpz.service.impl.TaskServiceImpl.mapTaskDtoToTask;
import static com.vlpz.service.impl.TaskServiceImpl.mapTaskToTaskDto;
import static com.vlpz.service.impl.UserServiceImpl.mapUserDtoToUser;
import static com.vlpz.service.impl.UserServiceImpl.mapUserToUserDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Override
    public List<StatisticsDto> getAllStatistics() {
        log.info("getAllStatistics: method is called");
        List<StatisticsDto> allStatistics = statisticsRepository.findAll().stream()
                .map(StatisticsServiceImpl::mapStatisticsToStatisticsDto)
                .collect(Collectors.toList());
        log.info("getAllStatistics: {} statistics records found", allStatistics.size());
        return allStatistics;
    }

    private boolean statisticsEntryExists(Long taskId, Long userId) {
        return statisticsRepository.getByTaskIdAndUserId(taskId, userId) != null;
    }

    private StatisticsDto getStatisticsByTaskIdAndUserId(Long taskId, Long userId) {
        log.info("getStatisticsByUserId: retrieving statistics of completed tasks for task id {} and user with id  {}",
                taskId,
                userId);
        return mapStatisticsToStatisticsDto(statisticsRepository.getByTaskIdAndUserId(taskId, userId));
    }

    @Override
    public List<StatisticsDto> getStatisticsByUserId(Long userId) {
        log.info("getStatisticsByUserId: retrieving statistics of completed tasks for user with id  {}", userId);
        List<StatisticsDto> statisticsPerUser = statisticsRepository.findAllByUserId(userId).stream()
                .map(StatisticsServiceImpl::mapStatisticsToStatisticsDto)
                .collect(Collectors.toList());
        log.info("getStatisticsByUserId: {} statistics records found", statisticsPerUser.size());
        return statisticsPerUser;
    }

    @Override
    public List<StatisticsDto> getStatisticsByTaskId(Long taskId) {
        log.info("getStatisticsByTaskId: retrieving statistics of completed tasks for task with id  {}", taskId);
        List<StatisticsDto> statisticsPerTask = statisticsRepository.findAllByTaskId(taskId).stream()
                .map(StatisticsServiceImpl::mapStatisticsToStatisticsDto)
                .collect(Collectors.toList());
        log.info("getStatisticsByTaskId: {} statistics records found", statisticsPerTask.size());
        return statisticsPerTask;
    }

    private StatisticsDto createStatistics(StatisticsDto statisticsDto) {
        Statistics statistics = mapStatisticsDtoToStatistics(statisticsDto);
        log.info("createStatistics: about to create a new statistics entry for task {} per user {}",
                statisticsDto.getTask().getId(), statisticsDto.getUser().getId());
        statistics = statisticsRepository.save(statistics);
        log.info("Statistics with id {} successfully created", statistics.getId());
        return mapStatisticsToStatisticsDto(statistics);
    }

    private StatisticsDto updateStatistics(StatisticsDto statisticsDto) {
        Statistics statistics = mapStatisticsDtoToStatistics(statisticsDto);
        log.info("updateStatistics: about to update a statistics entry {}", statistics);
        statistics = statisticsRepository.save(statistics);
        return mapStatisticsToStatisticsDto(statistics);
    }

    @Override
    public StatisticsDto logAttempt(TaskDto taskDto, UserDto userDto, boolean comparisonResult, long timeSpentOnAttempt) {
        log.info("logAttempt: logging attempt for task {} by user {}, result was successful - {}, time spent - {}",
                taskDto, userDto, comparisonResult, timeSpentOnAttempt);
        StatisticsDto statisticsDto;
        if (!statisticsEntryExists(taskDto.getId(), userDto.getId())) {
            statisticsDto = new StatisticsDto();
            statisticsDto.setTask(taskDto);
            statisticsDto.setUser(userDto);
            if (comparisonResult) {
                statisticsDto.setTimeSpentOnSuccessfulAttemptInSeconds(timeSpentOnAttempt);
            }
            statisticsDto.setCompleted(comparisonResult);
            statisticsDto.setTimeSpentTotalInSeconds(timeSpentOnAttempt);
            statisticsDto.setTimeSpentAverageInSeconds(timeSpentOnAttempt);
            statisticsDto.setTotalAttempts(1);
            statisticsDto = createStatistics(statisticsDto);
        } else {
            statisticsDto = getStatisticsByTaskIdAndUserId(taskDto.getId(), userDto.getId());
            if (comparisonResult) {
                if (statisticsDto.isCompleted()) {
                    if (statisticsDto.getTimeSpentOnSuccessfulAttemptInSeconds() > timeSpentOnAttempt) {
                        statisticsDto.setTimeSpentOnSuccessfulAttemptInSeconds(timeSpentOnAttempt);
                    }
                } else {
                    statisticsDto.setTimeSpentOnSuccessfulAttemptInSeconds(timeSpentOnAttempt);
                    statisticsDto.setCompleted(true);
                }
            }
            statisticsDto.setTotalAttempts(statisticsDto.getTotalAttempts() + 1);
            statisticsDto.setTimeSpentTotalInSeconds(statisticsDto.getTimeSpentTotalInSeconds() + timeSpentOnAttempt);
            statisticsDto.setTimeSpentAverageInSeconds(statisticsDto.getTimeSpentTotalInSeconds() / statisticsDto.getTotalAttempts());
            statisticsDto = updateStatistics(statisticsDto);
        }
        log.info("logAttempt: updated statistics {}", statisticsDto);
        return statisticsDto;
    }

    @Override
    public void deleteStatistics(Long id) {
        log.info("deleteStatistics: about to delete statistics entry with id {}", id);
        statisticsRepository.deleteById(id);
    }

    static Statistics mapStatisticsDtoToStatistics(StatisticsDto statisticsDto) {
        log.debug("mapStatisticsDtoToStatistics: map to Statistics from StatisticsDto: {}", statisticsDto);
        Statistics statistics = new Statistics();
        BeanUtils.copyProperties(statisticsDto, statistics);
        statistics.setTask(mapTaskDtoToTask(statisticsDto.getTask()));
        statistics.setUser(mapUserDtoToUser(statisticsDto.getUser()));
        return statistics;
    }

    static StatisticsDto mapStatisticsToStatisticsDto(Statistics statistics) {
        StatisticsDto statisticsDto = new StatisticsDto();
        BeanUtils.copyProperties(statistics, statisticsDto);
        statisticsDto.setTask(mapTaskToTaskDto(statistics.getTask()));
        statisticsDto.setUser(mapUserToUserDto(statistics.getUser()));
        log.debug("mapStatisticsToStatisticsDto: map from Statistics to StatisticsDto: {}", statisticsDto);
        return statisticsDto;
    }
}
