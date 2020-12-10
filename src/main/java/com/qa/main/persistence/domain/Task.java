package com.qa.main.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String title;

	@NotNull
	private String description;

	private boolean finished = false;

	public Task(Long id, String title, String description) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public Task(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

}
