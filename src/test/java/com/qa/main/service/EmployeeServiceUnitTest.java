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

import com.qa.main.dto.EmployeeDTO;
import com.qa.main.persistence.domain.Employee;
import com.qa.main.persistence.repo.EmployeeRepo;
import com.qa.main.util.SpringBeanUtil;

@SpringBootTest
@ActiveProfiles("dev")
public class EmployeeServiceUnitTest {

	@MockBean
	private EmployeeRepo repo;

	@MockBean
	private SpringBeanUtil util;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private EmployeeService service;

	private EmployeeDTO mapToDTO(Employee employee) {
		return this.mapper.map(employee, EmployeeDTO.class);
	}

	private final Employee TEST_1 = new Employee(1l, "Miles");
	private final Employee TEST_2 = new Employee(2l, "Peter");
	private final Employee TEST_3 = new Employee(3l, "Gwen");
	private final Employee TEST_4 = new Employee(2l, "MJ");
	private final Employee TEST_5 = new Employee(2l, "Genke");

	private List<EmployeeDTO> LISTOFEDTO;

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
	void readByID() throws Exception {
		when(repo.findById(TEST_2.getId())).thenReturn(Optional.of(TEST_2));
		assertThat(service.readByID(TEST_2.getId())).isEqualTo(mapToDTO(TEST_2));
		verify(repo, atLeastOnce()).findById(TEST_2.getId());
	}

	@Test
	void update() throws Exception {
		EmployeeDTO eDTO = mapToDTO(TEST_4);
		when(repo.findById(TEST_4.getId())).thenReturn(Optional.of(TEST_4));
		when(repo.save(TEST_4)).thenReturn(TEST_4);
		assertThat(service.update(eDTO, TEST_4.getId())).isEqualTo(eDTO);
		verify(repo, atLeastOnce()).findById(TEST_4.getId());
		verify(repo, atLeastOnce()).save(TEST_4);
	}

	@Test
	void delete() throws Exception {
		when(repo.existsById(TEST_5.getId())).thenReturn(false);
		assertThat(service.delete(TEST_5.getId())).isEqualTo(true);
		verify(repo, atLeastOnce()).deleteById(TEST_5.getId());
		verify(repo, atLeastOnce()).existsById(TEST_5.getId());
	}

}
