package com.qa.main.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.main.persistence.domain.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

}
