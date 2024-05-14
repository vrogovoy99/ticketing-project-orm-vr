package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.TaskService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@ConfigurationPropertiesBinding
public class TaskDtoConverter implements Converter<String, TaskDTO> {

    private final TaskService taskService;

    //injection
    public TaskDtoConverter(@Lazy TaskService taskService) {
        this.taskService = taskService;
    }

//    @Override
    public TaskDTO convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return taskService.findByTId(Long.parseLong(source));

    }

}
