package com.qa.main.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.main.persistence.domain.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	@Query(value = "SELECT * FROM EMPLOYEE WHERE NAME =?1", nativeQuery = true)
	Employee findByName(String name);
}
