package com.qa.main.integrated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.main.dto.EmployeeDTO;
import com.qa.main.persistence.domain.Employee;

@SpringBootTest
@AutoConfigureMockMvc
// sql runs in order schema followed by data file - JH dont make the mistake
@Sql(scripts = { "classpath:ToDo-schema.sql",
		"classpath:ToDo-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
public class EmployeeControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private EmployeeDTO mapToDTO(Employee emp) {
		return this.mapper.map(emp, EmployeeDTO.class);
	}

	ResultMatcher checkStatus;
	RequestBuilder request;

	// Test Subjects
	private final Employee TEST_1 = new Employee(1l, "Miles");
	private final Employee TEST_2 = new Employee(2l, "Peter");
	private final Employee TEST_3 = new Employee(3l, "Gwen");
	private final Employee TEST_4 = new Employee(4l, "MJ");
	private final Employee TEST_5 = new Employee(5l, "Genke");

	private final String URI = "/employee";

	@Test
	void createTest() throws Exception {
		EmployeeDTO testDTO = mapToDTO(new Employee("Aaron"));
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "/create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isCreated();

		EmployeeDTO testSavedDTO = mapToDTO(new Employee("Aaron"));
		testSavedDTO.setId(6l);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void readByIDTest() throws Exception {

		RequestBuilder request = get(URI + "/readByID/" + TEST_2.getId());
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkContent = content().contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkBody = jsonPath("name").value(TEST_2.getName());

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent).andExpect(checkBody);
	}

	@Test
	void updateTest() throws Exception {
		EmployeeDTO UpdatedDTO = mapToDTO(new Employee("Aaron"));
		String UpdatedDTOAsJson = jsonifier.writeValueAsString(UpdatedDTO);

		RequestBuilder request = put(URI + "/update/" + TEST_4.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(UpdatedDTOAsJson);
		ResultMatcher checkStatus = status().isAccepted();

		EmployeeDTO testSavedDTO = mapToDTO(new Employee("Aaron"));
		testSavedDTO.setId(4l);

		ResultMatcher checkBody = jsonPath("name").value(testSavedDTO.getName());

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void deleteTest() throws Exception {
		RequestBuilder request = delete(URI + "/delete/" + TEST_5.getId());
		ResultMatcher checkStatus = status().isNoContent();

		this.mvc.perform(request).andExpect(checkStatus);
	}
}
