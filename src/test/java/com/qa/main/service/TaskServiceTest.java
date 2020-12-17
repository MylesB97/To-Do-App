package com.qa.main.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.main.dto.TaskDTO;
import com.qa.main.persistence.domain.Task;
import com.qa.main.persistence.repo.TaskRepo;
import com.qa.main.util.SpringBeanUtil;

@SpringBootTest
@ActiveProfiles("dev")
public class TaskServiceTest {

	@MockBean
	private TaskRepo repo;

	@MockBean
	private SpringBeanUtil util;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private TaskService service;

	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}

	private final Task TEST_1 = new Task(1l, "Task 1", "Do the task");
	private final Task TEST_2 = new Task(1l, "Task 2", "Do the task");
	private final Task TEST_3 = new Task(1l, "Task 3", "Do the task");
	private final Task TEST_4 = new Task(1l, "Task 4", "Do the task");
	private final Task TEST_5 = new Task(1l, "Task 5", "Do the task");

	private List<TaskDTO> LISTOFEDTO;

	@BeforeEach
	void init() {
		LISTOFEDTO = List.of(mapToDTO(TEST_1), mapToDTO(TEST_2), mapToDTO(TEST_3), mapToDTO(TEST_4), mapToDTO(TEST_5));
	}

	@Test
	void createTest() throws Exception {
		when(repo.save(TEST_1)).thenReturn(TEST_1);
		assertThat(service.create(TEST_1)).isEqualTo(this.mapToDTO(TEST_1));
		verify(repo, atLeastOnce()).save(TEST_1);
	}

	@Test
	void readAllTest() throws Exception {
		when(repo.findAll()).thenReturn(List.of(TEST_1, TEST_2, TEST_3, TEST_4, TEST_5));
		assertThat(service.readAll()).isEqualTo(LISTOFEDTO);
		verify(repo, atLeastOnce()).findAll();
	}

	@Test
	void update() throws Exception {
		TaskDTO eDTO = mapToDTO(TEST_4);
		when(repo.findById(TEST_4.getId())).thenReturn(Optional.of(TEST_4));
		when(repo.save(TEST_4)).thenReturn(TEST_4);
		assertThat(service.update(eDTO, TEST_4.getId())).isEqualTo(eDTO);
		verify(repo, atLeastOnce()).findById(TEST_4.getId());
		verify(repo, atLeastOnce()).save(TEST_4);
	}

//	@Test
//	void updateDescription() throws Exception {
//		TaskDTO eDTO = mapToDTO(TEST_4);
//		when(repo.findById(TEST_4.getId())).thenReturn(Optional.of(TEST_4));
//		when(repo.save(TEST_4)).thenReturn(TEST_4);
//		assertThat(service.updateDescription(eDTO, TEST_4.getId())).isEqualTo(eDTO);
//		verify(repo, atLeastOnce()).findById(TEST_4.getId());
//		verify(repo, atLeastOnce()).save(TEST_4);
//	}
//
//	@Test
//	void updateFinished() throws Exception {
//		TaskDTO eDTO = mapToDTO(TEST_4);
//		when(repo.findById(TEST_4.getId())).thenReturn(Optional.of(TEST_4));
//		when(repo.save(TEST_4)).thenReturn(TEST_4);
//		assertThat(service.updateFinished(eDTO, TEST_4.getId())).isEqualTo(eDTO);
//		verify(repo, atLeastOnce()).findById(TEST_4.getId());
//		verify(repo, atLeastOnce()).save(TEST_4);
//	}

	@Test
	void delete() throws Exception {
		when(repo.existsById(TEST_5.getId())).thenReturn(false);
		assertThat(service.delete(TEST_5.getId())).isEqualTo(true);
		verify(repo, atLeastOnce()).deleteById(TEST_5.getId());
		verify(repo, atLeastOnce()).existsById(TEST_5.getId());
	}
}
