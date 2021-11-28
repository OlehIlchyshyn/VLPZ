package com.vlpz.service.impl;

import com.vlpz.dto.TaskDto;
import com.vlpz.model.Task;
import com.vlpz.model.enums.Complexity;
import com.vlpz.repository.TaskRepository;
import com.vlpz.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<TaskDto> getAllTasks() {
        log.info("getAllTasks: method is called");
        List<TaskDto> allTasks = taskRepository.findAll().stream()
                .map(TaskServiceImpl::mapTaskToTaskDto)
                .collect(Collectors.toList());
        log.info("getAllTasks: {} tasks found", allTasks.size());
        return allTasks;
    }

    @Override
    public TaskDto getTask(Long id) {
        log.info("getTask: retrieving task by id {}", id);
        TaskDto taskDto = mapTaskToTaskDto(taskRepository.getById(id));
        log.info("getTask: got task - {}", taskDto);
        return taskDto;
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = mapTaskDtoToTask(taskDto);
        log.info("createTask: about to create a new task with caption {}", task.getCaption());
        task = taskRepository.save(task);
        log.info("Task with id {} successfully created", task.getId());
        return mapTaskToTaskDto(task);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        Task task = mapTaskDtoToTask(taskDto);
        log.info("updateTask: about to update task {}", task);
        task = taskRepository.save(task);
        return mapTaskToTaskDto(task);
    }

    @Override
    public void deleteTask(Long id) {
        log.info("deleteTask: about to delete task with id {}", id);
        taskRepository.deleteById(id);
    }

    public static Task mapTaskDtoToTask(TaskDto taskDto) {
        log.debug("mapTaskDtoToTask: map to Task from TaskDto: {}", taskDto);
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        task.setComplexity(Complexity.valueOf(taskDto.getComplexity()));
        return task;
    }

    public static TaskDto mapTaskToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(task, taskDto);
        taskDto.setComplexity(task.getComplexity().name());
        log.debug("mapTaskToTaskDto: map from Task to TaskDto: {}", taskDto);
        return taskDto;
    }
}
