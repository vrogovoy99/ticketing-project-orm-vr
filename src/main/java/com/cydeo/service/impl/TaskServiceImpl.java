package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.TaskRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll()
                .stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO taskDTO) {
        //saves new task

        Task task1 = taskMapper.convertToEntity(taskDTO);

        if (task1.getTaskStatus() == null) {
            task1.setTaskStatus(Status.OPEN);
        }

        if (task1.getAssignedDate() == null) {
            task1.setAssignedDate(LocalDate.now());
        }

        if (task1.getTid() == null) {
            task1.setTid(UUID.randomUUID().getMostSignificantBits());
        }

        task1.setProject(projectRepository.findByProjectCode(task1.getProject().getProjectCode()));
        task1.setAssignedEmployee(userRepository.findByUserName(task1.getAssignedEmployee().getUserName()));
        taskRepository.save(task1);


    }

    @Override
    public TaskDTO findById(long l) {
        return taskMapper.convertToDto(taskRepository.findById(l).get());
    }

    @Override
    public TaskDTO findByTId(long l) {
        return taskMapper.convertToDto(taskRepository.findByTid(l));
    }
}
