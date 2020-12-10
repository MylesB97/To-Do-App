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

import com.qa.main.dto.EmployeeDTO;
import com.qa.main.persistence.domain.Employee;
import com.qa.main.service.EmployeeService;

@SpringBootTest
@ActiveProfiles("dev")
public class EmployeeControllerUnitTest {

	@Autowired
	private EmployeeController controller;

	@MockBean
	private EmployeeService service;

	@Autowired
	private ModelMapper mapper;

	private EmployeeDTO mapToDTO(Employee employee) {
		return this.mapper.map(employee, EmployeeDTO.class);
	}

	// Test Subjects
	private final Employee TEST_EMPLOYEE_1 = new Employee(1l, "Miles");
	private final Employee TEST_EMPLOYEE_2 = new Employee(2l, "Peter");
	private final Employee TEST_EMPLOYEE_3 = new Employee(3l, "Gwen");
	private final Employee TEST_EMPLOYEE_4 = new Employee(2l, "MJ");
	private final Employee TEST_EMPLOYEE_5 = new Employee(2l, "Genke");

	// List of Subjects
	private final List<Employee> LISTOFEMPLOYEES = List.of(TEST_EMPLOYEE_1, TEST_EMPLOYEE_2, TEST_EMPLOYEE_3,
			TEST_EMPLOYEE_4, TEST_EMPLOYEE_5);

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_EMPLOYEE_1)).thenReturn(this.mapToDTO(TEST_EMPLOYEE_1));
		assertThat(new ResponseEntity<EmployeeDTO>(this.mapToDTO(TEST_EMPLOYEE_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_EMPLOYEE_1));
		verify(this.service, atLeastOnce()).create(TEST_EMPLOYEE_1);
	}

	// Read All
	@Test
	void readAllTest() throws Exception {
		List<EmployeeDTO> dtos = LISTOFEMPLOYEES.stream().map(this::mapToDTO).collect(Collectors.toList());
		when(this.service.readAll()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
	}

	// Read by ID
	@Test
	void readByIDTest() throws Exception {
		when(this.service.readByID(TEST_EMPLOYEE_5.getId())).thenReturn(this.mapToDTO(TEST_EMPLOYEE_5));
		assertThat(new ResponseEntity<EmployeeDTO>(this.mapToDTO(TEST_EMPLOYEE_5), HttpStatus.OK))
				.isEqualTo(this.controller.readByID(TEST_EMPLOYEE_5.getId()));
		verify(this.service, atLeastOnce()).readByID(TEST_EMPLOYEE_5.getId());
	}

	// Read by Name

	// Update

	// Delete
}
