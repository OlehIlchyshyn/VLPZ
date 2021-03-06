package com.vlpz.controller;

import com.vlpz.api.TaskApi;
import com.vlpz.controller.assembler.TaskModelAssembler;
import com.vlpz.controller.model.TaskModel;
import com.vlpz.dto.StatisticsDto;
import com.vlpz.dto.TaskAnswerDto;
import com.vlpz.dto.TaskDto;
import com.vlpz.model.User;
import com.vlpz.service.StatisticsService;
import com.vlpz.service.TaskService;
import com.vlpz.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.vlpz.service.impl.UserServiceImpl.mapUserToUserDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController implements TaskApi {

    private final TaskService taskService;
    private final StatisticsService statisticsService;
    private final ValidationService validationService;
    private final TaskModelAssembler modelAssembler;

    @Override
    public CollectionModel<TaskModel> getAllTasks() {
        return modelAssembler.toCollectionModel(taskService.getAllTasks());
    }

    @Override
    public TaskModel getTask(Long id) {
        return modelAssembler.toModel(taskService.getTask(id));

    }

    @Override
    public TaskModel createTask(TaskDto taskDto) {
        return modelAssembler.toModel(taskService.createTask(taskDto));
    }

    @Override
    public TaskModel updateTask(Long id, TaskDto taskDto) {
        TaskDto taskById = taskService.getTask(id);
        if (taskDto.getCaption() != null) {
            taskById.setCaption(taskDto.getCaption());
        }
        if (taskDto.getDescription() != null) {
            taskById.setDescription(taskDto.getDescription());
        }
        if (taskDto.getComplexity() != null) {
            taskById.setComplexity(taskDto.getComplexity());
        }
        if (taskDto.getSolutionDiagramJson() != null) {
            taskById.setSolutionDiagramJson(taskDto.getSolutionDiagramJson());
        }
        return modelAssembler.toModel(taskService.updateTask(taskById));
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public StatisticsDto submitResult(User user, Long taskId, TaskAnswerDto taskAnswerDto) {
        String solutionDiagramJson = taskService.getTask(taskId).getSolutionDiagramJson();
        boolean comparisonResult =  validationService.areDiagramsEqual(
                solutionDiagramJson,
                taskAnswerDto.getSubmittedDiagramJson());
        return statisticsService.logAttempt(
                taskService.getTask(taskId),
                mapUserToUserDto(user),
                comparisonResult,
                taskAnswerDto.getTimeSpentOnAttemptInSecond());
    }

    @Override
    public List<StatisticsDto> getStatistics(Long id) {
        return statisticsService.getStatisticsByTaskId(id);
    }
}
