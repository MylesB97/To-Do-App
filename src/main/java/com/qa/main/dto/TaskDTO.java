package com.qa.main.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO {

	private Long id;
	private String title;
	private String description;
	private boolean finished;

}