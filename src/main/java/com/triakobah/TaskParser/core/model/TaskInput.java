package com.triakobah.TaskParser.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskInput extends Task{
    private List<String> requires;

    public TaskInput(String name, String command, List<String> requires) {
        super(name, command);
        this.requires = requires;
    }
}
