package com.cydeo.entity;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tasks")
@Where(clause = "is_deleted=false")
public class Task extends BaseEntity{

//    private Long tid;
    private String taskSubject;
    private String taskDetail;
    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;
    @Enumerated(EnumType.STRING)
    private Status taskStatus=Status.OPEN;

    @ManyToOne (fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne (fetch = FetchType.LAZY)
    private User assignedEmployee;

}
