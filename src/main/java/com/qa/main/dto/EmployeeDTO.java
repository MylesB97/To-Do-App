package com.qa.main.dto;

import java.util.ArrayList;
import java.util.List;

import com.qa.main.persistence.domain.Task;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDTO {

	private Long id;
	private String name;

	private List<Task> tasks = new ArrayList<>();
}
