package org.hei.kdot.task.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Step {

    private String id;
    private String title;
    private String description;
    private boolean isCompleted;
    private String idTask;
}