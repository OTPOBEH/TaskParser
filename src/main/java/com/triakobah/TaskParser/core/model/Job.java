package com.triakobah.TaskParser.core.model;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Valid
    private List<TaskInput> tasks;

}
