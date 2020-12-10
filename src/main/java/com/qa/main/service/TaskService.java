package com.qa.main.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.main.dto.TaskDTO;
import com.qa.main.persistence.domain.Task;
import com.qa.main.persistence.repo.TaskRepo;
import com.qa.main.util.SpringBeanUtil;

@Service
public class TaskService {

	private TaskRepo repo;

	private ModelMapper mapper;

	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}

	@Autowired
	public TaskService(TaskRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public TaskDTO create(Task task) {
		return this.mapToDTO(this.repo.save(task));
	}

	// Read All Method
	public List<TaskDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	// Update Title
	public TaskDTO updateTitle(TaskDTO tDTO, Long id) {
		Task toUpdate = this.repo.findById(id).orElseThrow();
		toUpdate.setTitle(tDTO.getTitle());
		SpringBeanUtil.mergeNotNull(tDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

	// Update Description
	public TaskDTO updateDescription(TaskDTO tDTO, Long id) {
		Task toUpdate = this.repo.findById(id).orElseThrow();
		toUpdate.setDescription(tDTO.getDescription());
		SpringBeanUtil.mergeNotNull(tDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

	// Update Finished
	public TaskDTO updateFinished(TaskDTO tDTO, Long id) {
		Task toUpdate = this.repo.findById(id).orElseThrow();
		toUpdate.setFinished(tDTO.isFinished());
		SpringBeanUtil.mergeNotNull(tDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
