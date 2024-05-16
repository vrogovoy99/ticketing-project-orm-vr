package com.cydeo.repository;

import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
//    Task findByTid(long l);

    List<Task> findByTaskStatusIsNot(Status status);

    List<Task> findByTaskStatus(Status status);
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus != 'Completed'")
    int totalNonCompletedTasks(String projectCode);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus = 'Completed'")
    int totalCompletedTasks(String projectCode);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tasks SET is_deleted = true WHERE project_id in " +
            " (SELECT id FROM projects WHERE project_code = ?1)", nativeQuery = true)
    void deleteByProjectCode(String projectCode);
}
