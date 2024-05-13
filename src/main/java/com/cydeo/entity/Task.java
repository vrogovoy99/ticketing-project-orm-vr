package com.cydeo.entity;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "tasks")
@Where(clause = "is_deleted=false")
public class Task extends BaseEntity{


    private String taskSubject;
    private String taskDetail;
    private LocalDate assignedDate;
    @Enumerated(EnumType.STRING)
    private Status taskStatus=Status.OPEN;

    @ManyToOne
    private Project project;
    @ManyToOne
    private User assignedEmployee;

}
