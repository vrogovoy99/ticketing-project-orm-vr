package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    ProjectDTO findById(Long id);

    List<ProjectDTO> findAll();

    void save(ProjectDTO projectDTO);

    void deleteById(String projectcode);

    void complete(String projectcode);

    Object findByProjectCode(String projectcode);

    void update(ProjectDTO project);

    List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager);
}
