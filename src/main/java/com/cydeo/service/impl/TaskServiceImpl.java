package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.TaskRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Override
    public void deleteById(Long tid) {
        //get task from db
        Task task = taskRepository.findByTid(tid);
        //change is_deleted
        task.setIsDeleted(true);
        //save task
        taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO taskDTO) {
        Task taskEntity = taskRepository.findByTid(taskDTO.getTid());

        taskDTO.setTaskStatus(taskEntity.getTaskStatus());
        taskDTO.setAssignedDate(taskEntity.getAssignedDate());

        Task task = taskMapper.convertToEntity(taskDTO);
        //reset reference objects ids
        task.setAssignedEmployee(userRepository.findByUserName(taskDTO.getAssignedEmployee().getUserName()));
        task.setProject(projectRepository.findByProjectCode(taskDTO.getProject().getProjectCode()));
        task.setId(taskEntity.getId());

        taskRepository.save(task);


    }

    @Override
    public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {

        List<Task> taskList = taskRepository.findByTaskStatusIsNot(status);
        List<TaskDTO> taskDTOList = taskList.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
        return taskDTOList;

    }

    @Override
    public void updateStatus(TaskDTO taskDTO) {
        Task taskEntity = taskRepository.findByTid(taskDTO.getTid());
        taskEntity.setTaskStatus(taskDTO.getTaskStatus());
        taskRepository.save(taskEntity);
    }

    @Override
    public List<TaskDTO> findAllTasksByStatus(Status status) {

        return taskRepository.findByTaskStatus(status).stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }
}
