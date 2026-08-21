package com.juanmatiaslopez.taskmanager.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.juanmatiaslopez.taskmanager.Enum.Category;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO {
    private long id;
    private String title;
    private String description;
    private Category category;
    private boolean isCompleted;
    private UserDTO user;
}
