package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> findAll();

    void save(TaskDTO task);

    TaskDTO findById(long l);

    TaskDTO findByTId(long l);
}
