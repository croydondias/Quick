package com.croydon.quick.service;

import java.util.List;

import com.croydon.quick.domain.Task;
import com.croydon.quick.exception.TaskAlreadyExistsException;

public interface TaskService {

	List<Task> findAll();
	Task findOne(Long id);
	Task save(Task task) throws TaskAlreadyExistsException;
	void delete(Long id);
}
