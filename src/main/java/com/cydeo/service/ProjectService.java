package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    ProjectDTO findById(Long id);

    List<ProjectDTO> findAll();

    void save(ProjectDTO projectDTO);
}
