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

import com.qa.main.dto.TaskDTO;
import com.qa.main.persistence.domain.Task;
import com.qa.main.service.TaskService;

@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

	private TaskService service;

	@Autowired
	public TaskController(TaskService service) {
		super();
		this.service = service;
	}

	// Create Method
	@PostMapping("/create")
	public ResponseEntity<TaskDTO> create(@RequestBody Task task) {
		TaskDTO created = this.service.create(task);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// Read All Method
	@GetMapping("/read")
	public ResponseEntity<List<TaskDTO>> read() {
		return ResponseEntity.ok(this.service.readAll());
	}

	// Update Title
	@PutMapping("/update-title/{id}")
	public ResponseEntity<TaskDTO> updateTitle(@PathVariable Long id, @RequestBody TaskDTO tDTO) {
		return new ResponseEntity<>(this.service.updateTitle(tDTO, id), HttpStatus.ACCEPTED);
	}

	// Update Description
	@PutMapping("/update-desc/{id}")
	public ResponseEntity<TaskDTO> updateDesc(@PathVariable Long id, @RequestBody TaskDTO tDTO) {
		return new ResponseEntity<>(this.service.updateDescription(tDTO, id), HttpStatus.ACCEPTED);
	}

	// Update Finished
	@PutMapping("/update-finished/{id}")
	public ResponseEntity<TaskDTO> updateFinished(@PathVariable Long id, @RequestBody TaskDTO tDTO) {
		return new ResponseEntity<>(this.service.updateFinished(tDTO, id), HttpStatus.ACCEPTED);
	}

	// Update Delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TaskDTO> delete(@RequestBody Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) // If Deletion is successful
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // If the record isn't found
	}

}
