package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
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

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
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
        project.setIsDeleted(true);
        projectRepository.save(project);
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
//        Project p2 = projectMapper.convertToEntity(project);
//        p2.setId(p.getId());
//        p2.setProjectStatus(p.getProjectStatus());
//
//        projectRepository.save(p2);

    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {

        return projectRepository.findByAssignedManager(userRepository.findByUserName(manager.getUserName()))
                .stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }
}
