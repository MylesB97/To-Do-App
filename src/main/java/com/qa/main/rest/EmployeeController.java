package com.qa.main.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.main.dto.EmployeeDTO;
import com.qa.main.persistence.domain.Employee;
import com.qa.main.service.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

	private EmployeeService service;

	@Autowired
	public EmployeeController(EmployeeService service) {
		super();
		this.service = service;
	}

	// Create Method
	@PostMapping("/create")
	public ResponseEntity<EmployeeDTO> create(@RequestBody Employee employee) {
		EmployeeDTO created = this.service.create(employee);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// Read All Method
	@GetMapping("/read")
	public ResponseEntity<List<EmployeeDTO>> read() {
		return ResponseEntity.ok(this.service.readAll());
	}

	// Read By ID Method
	@GetMapping("/readByID/{id}")
	public ResponseEntity<EmployeeDTO> readByID(@PathVariable("id") Long id) {
		return ResponseEntity.ok(this.service.readByID(id));
	}

	// Update
	@PutMapping("/update/{id}")
	public ResponseEntity<EmployeeDTO> update(@PathVariable("id") Long id, @RequestBody EmployeeDTO eDTO) {
		return new ResponseEntity<>(this.service.update(eDTO, id), HttpStatus.ACCEPTED);
	}

	// Delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<EmployeeDTO> delete(@PathVariable("id") Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) // If Deletion is successful
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // If the record isn't found
	}

}
