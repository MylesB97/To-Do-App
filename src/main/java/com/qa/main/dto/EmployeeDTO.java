package com.qa.main.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDTO {

	private Long id;
	private String name;

	private List<TaskDTO> ListOfTasks = new ArrayList<>();
}
