package com.qa.main.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.main.dto.EmployeeDTO;
import com.qa.main.persistence.domain.Employee;
import com.qa.main.persistence.repo.EmployeeRepo;
import com.qa.main.util.SpringBeanUtil;

@Service
public class EmployeeService {
	private EmployeeRepo repo;
	private ModelMapper mapper;

	private EmployeeDTO mapToDTO(Employee employee) {
		return this.mapper.map(employee, EmployeeDTO.class);
	}

	@Autowired
	public EmployeeService(EmployeeRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// Create
	public EmployeeDTO create(Employee employee) {
		return this.mapToDTO(this.repo.save(employee));
	}

	// Read All
	public List<EmployeeDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	// Read By ID
	public EmployeeDTO readByID(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}

	// Read By Name
	public EmployeeDTO readByName(String name) {
		return this.mapToDTO(this.repo.findByName(name));
	}

	// Update
	public EmployeeDTO update(EmployeeDTO eDTO, Long id) {
		Employee toUpdate = this.repo.findById(id).orElseThrow(); // EmployeenNotFoundException::new
		toUpdate.setName(eDTO.getName());
		SpringBeanUtil.mergeNotNull(eDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

	// Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
