package com.cydeo.repository;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectCode(String projectcode);

    List<Project> findByAssignedManager(User manager);
}
