package com.vlpz.controller.assembler;

import com.vlpz.controller.TaskController;
import com.vlpz.controller.model.TaskModel;
import com.vlpz.dto.TaskDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskModelAssembler extends RepresentationModelAssemblerSupport<TaskDto, TaskModel> {
    public static final String GET_REL = "get";
    public static final String UPDATE_REL = "update";
    public static final String DELETE_REL = "delete";

    public TaskModelAssembler() {
        super(TaskController.class, TaskModel.class);
    }

    @Override
    public TaskModel toModel(TaskDto entity) {
        TaskModel taskModel = new TaskModel(entity);

        Link getLink = linkTo(methodOn(TaskController.class).getTask(entity.getId())).withRel(GET_REL);
        Link updateLink = linkTo(methodOn(TaskController.class).updateTask(entity.getId(), new TaskDto())).withRel(UPDATE_REL);
        Link deleteLink = linkTo(methodOn(TaskController.class).deleteTask(entity.getId())).withRel(DELETE_REL);

        return taskModel.add(getLink, updateLink, deleteLink);
    }

    public CollectionModel<TaskModel> toCollectionModel(List<TaskDto> taskDtos) {
        List<TaskModel> taskModels = taskDtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        Link self = linkTo((methodOn(TaskController.class).getAllTasks())).withSelfRel();
        return CollectionModel.of(taskModels).add(self);
    }
}
