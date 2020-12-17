package com.qa.main.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.main.dto.TaskDTO;
import com.qa.main.persistence.domain.Task;
import com.qa.main.service.TaskService;

@SpringBootTest
@ActiveProfiles("dev")
public class TaskControllerUnitTest {

	@Autowired
	private TaskController controller;

	@MockBean
	private TaskService service;

	@Autowired
	private ModelMapper mapper;

	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}

	// Test Data
	private final Task TEST_TASK_1 = new Task(1l, "Task 1", "Do the task");
	private final Task TEST_TASK_2 = new Task(1l, "Task 2", "Do the task");
	private final Task TEST_TASK_3 = new Task(1l, "Task 3", "Do the task");
	private final Task TEST_TASK_4 = new Task(1l, "Task 4", "Do the task");
	private final Task TEST_TASK_5 = new Task(1l, "Task 5", "Do the task");

	private final List<Task> LIST_OF_TASK = List.of(TEST_TASK_1, TEST_TASK_2, TEST_TASK_3, TEST_TASK_4, TEST_TASK_5);

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_TASK_1)).thenReturn(this.mapToDTO(TEST_TASK_1));
		assertThat(new ResponseEntity<TaskDTO>(this.mapToDTO(TEST_TASK_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_TASK_1));
		verify(this.service, atLeastOnce()).create(TEST_TASK_1);
	}

	// Read All
	@Test
	void readAllTest() throws Exception {
		List<TaskDTO> dtos = LIST_OF_TASK.stream().map(this::mapToDTO).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
		verify(this.service, atLeastOnce()).readAll();
	}

	// Update Title
	@Test
	void updateTest() throws Exception {
		when(this.service.update(this.mapToDTO(TEST_TASK_4), TEST_TASK_4.getId()))
				.thenReturn(this.mapToDTO(TEST_TASK_4));
		assertThat(new ResponseEntity<TaskDTO>(this.mapToDTO(TEST_TASK_4), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_TASK_4.getId(), this.mapToDTO(TEST_TASK_4)));
		verify(this.service, atLeastOnce()).update(this.mapToDTO(TEST_TASK_4), TEST_TASK_4.getId());
	}
//
//	// Update Description
//	@Test
//	void updateDescTest() throws Exception {
//		when(this.service.updateDescription(this.mapToDTO(TEST_TASK_2), TEST_TASK_2.getId()))
//				.thenReturn(this.mapToDTO(TEST_TASK_2));
//		assertThat(new ResponseEntity<TaskDTO>(this.mapToDTO(TEST_TASK_2), HttpStatus.ACCEPTED))
//				.isEqualTo(this.controller.updateDesc(TEST_TASK_2.getId(), this.mapToDTO(TEST_TASK_2)));
//		verify(this.service, atLeastOnce()).updateDescription(this.mapToDTO(TEST_TASK_2), TEST_TASK_2.getId());
//	}
//
//	// Update Finished
//	@Test
//	void updateFinishedTest() throws Exception {
//		when(this.service.updateFinished(this.mapToDTO(TEST_TASK_3), TEST_TASK_3.getId()))
//				.thenReturn(this.mapToDTO(TEST_TASK_3));
//		assertThat(new ResponseEntity<TaskDTO>(this.mapToDTO(TEST_TASK_3), HttpStatus.ACCEPTED))
//				.isEqualTo(this.controller.updateFinished(TEST_TASK_3.getId(), this.mapToDTO(TEST_TASK_3)));
//		verify(this.service, atLeastOnce()).updateFinished(this.mapToDTO(TEST_TASK_3), TEST_TASK_3.getId());
//	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_TASK_5.getId())).thenReturn(true);
		assertThat(this.controller.delete(TEST_TASK_5.getId())).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		verify(this.service, atLeastOnce()).delete(TEST_TASK_5.getId());
	}
}
