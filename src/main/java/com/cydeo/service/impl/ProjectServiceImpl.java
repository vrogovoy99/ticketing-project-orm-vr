package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.TaskRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserRepository userRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
        this.taskService = taskService;
    }


    @Override
    public ProjectDTO findById(Long id) {
        return projectMapper.convertToDto(projectRepository.findById(id).get());
    }

    @Override
    public List<ProjectDTO> findAll() {
        return projectRepository.findAll().stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {

        // replace DTO derived manager user with actual user record
        Project project = projectMapper.convertToEntity(projectDTO);
        project.setAssignedManager(userRepository.findByUserName(project.getAssignedManager().getUserName()));

        projectRepository.save(project);
    }

    @Override
    public void deleteById(String projectcode) {
        Project project = projectRepository.findByProjectCode(projectcode);
        //if project has no tasks then delete it.

         if (taskService.totalNonCompletedTasks(project.getProjectCode()) == 0) {
            //remove completed tasks
             taskService.deleteByProjectCode(project.getProjectCode());
             //"delete" project
             project.setIsDeleted(true);
             projectRepository.save(project);
         }
        System.out.println("Project can not be deleted, since it has non-completed tasks");

    }

    @Override
    public void complete(String projectcode) {
        Project project = projectRepository.findByProjectCode(projectcode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

    }

    @Override
    public ProjectDTO findByProjectCode(String projectcode) {
        return projectMapper.convertToDto(projectRepository.findByProjectCode(projectcode));
    }

    @Override
    public void update(ProjectDTO project) {
        //find database object to be updated
        Project p = projectRepository.findByProjectCode(project.getProjectCode());
        //override database object with DTO values
        p.setProjectName(project.getProjectName());
        p.setStartDate(project.getStartDate());
        p.setEndDate(project.getEndDate());
        p.setProjectDetail(project.getProjectDetail());
        p.setAssignedManager(userRepository.findByUserName(project.getAssignedManager().getUserName()));
        projectRepository.save(p);
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
//        List<ProjectDTO> projectDTOList=projectRepository.findByAssignedManager(userRepository.findByUserName(manager.getUserName()))
//                .stream().map(projectMapper::convertToDto).collect(Collectors.toList());

        List<ProjectDTO> projectDTOList=projectRepository.findByAssignedManager(userRepository.findByUserName(manager.getUserName()))
                .stream().map(entity -> {
                    ProjectDTO dto = projectMapper.convertToDto(entity);
                    dto.setCompleteTaskCounts(taskService.totalNonCompletedTasks(dto.getProjectCode()));
                    dto.setUnfinishedTaskCounts(taskService.totalCompletedTasks(dto.getProjectCode()));
                    return dto;
                }).collect(Collectors.toList());

        return projectDTOList;
    }
}
