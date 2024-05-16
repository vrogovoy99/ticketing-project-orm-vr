package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {
    List<TaskDTO> findAll();

    void save(TaskDTO task);

    TaskDTO findById(long l);

//    TaskDTO findByTId(long l);

    void deleteById(Long tid);

    void update(TaskDTO task);

    List<TaskDTO> findAllTasksByStatusIsNot(Status status);

    void updateStatus(TaskDTO task);

    List<TaskDTO> findAllTasksByStatus(Status status);
}
