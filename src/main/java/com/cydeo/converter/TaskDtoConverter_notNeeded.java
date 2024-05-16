package com.cydeo.converter;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.TaskService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@ConfigurationPropertiesBinding
public class TaskDtoConverter_notNeeded implements Converter<String, TaskDTO> {
// not needed, since we do not have dropdowns with tasks that will need to be converted to object task
    private final TaskService taskService;

    //injection
    public TaskDtoConverter_notNeeded(@Lazy TaskService taskService) {
        this.taskService = taskService;
    }

//    @Override
    public TaskDTO convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return taskService.findById(Long.parseLong(source));

    }

}
