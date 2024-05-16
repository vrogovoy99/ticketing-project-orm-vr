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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.util.ClassUtils.isPresent;

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

//        if (task1.getTid() == null) {
//            task1.setTid(UUID.randomUUID().getMostSignificantBits());
//        }
//after addind id field in projectDTO below conversion is not needed, since mapper transfers is valuses between entity and dto
//        task1.setProject(projectRepository.findByProjectCode(task1.getProject().getProjectCode()));
        task1.setAssignedEmployee(userRepository.findByUserName(task1.getAssignedEmployee().getUserName()));
        taskRepository.save(task1);


    }

    @Override
    public TaskDTO findById(long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
                return taskMapper.convertToDto(task.get());
            }
        return null;
    }
//
//    @Override
//    public TaskDTO findByTId(long l) {
//        return taskMapper.convertToDto(taskRepository.findByTid(l));
//    }

    @Override
    public void deleteById(Long id) {
        //get task from db
//        Task task = taskRepository.findByTid(tid);
        Task task = taskRepository.findById(id).get();
        //change is_deleted
        task.setIsDeleted(true);
        //save task
        taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO taskDTO) {
        Optional<Task> taskEntity = taskRepository.findById(taskDTO.getId());
        Task taskConvert = taskMapper.convertToEntity(taskDTO);


        if (taskEntity.isPresent()){
            taskConvert.setTaskStatus(taskEntity.get().getTaskStatus());
            taskConvert.setAssignedDate(taskEntity.get().getAssignedDate());
            taskConvert.setAssignedEmployee(userRepository.findByUserName(taskDTO.getAssignedEmployee().getUserName()));
            taskRepository.save(taskConvert);
        }
    }

    @Override
    public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {

        return taskRepository.findByTaskStatusIsNot(status).stream().map(taskMapper::convertToDto).collect(Collectors.toList());

    }

    @Override
    public void updateStatus(TaskDTO taskDTO) {
        Task taskEntity = taskRepository.findById(taskDTO.getId()).get();
        taskEntity.setTaskStatus(taskDTO.getTaskStatus());
        taskRepository.save(taskEntity);
    }

    @Override
    public List<TaskDTO> findAllTasksByStatus(Status status) {

        return taskRepository.findByTaskStatus(status).stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public int totalNonCompletedTasks(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProjectCode(String projectCode) {
        taskRepository.deleteByProjectCode(projectCode);
    }
}
