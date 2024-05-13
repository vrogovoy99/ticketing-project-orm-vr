package com.cydeo.entity;

import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "projects")
public class Project extends BaseEntity{

    private String projectName;
    private String projectCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectDetail;
    private int completeTaskCounts;
    private int unfinishedTaskCounts;

    @Enumerated(EnumType.STRING)
    private Status projectStatus=Status.OPEN;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    private User assignedManager;


}
