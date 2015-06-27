package com.croydon.quick.service;

import java.util.List;

import com.croydon.quick.domain.Task;
import com.croydon.quick.exception.TaskAlreadyExistsException;
import com.croydon.quick.exception.TaskDoesntExistException;

public interface TaskService {

	List<Task> findAll();
	Task findOne(Long id);
	Task create(Task task) throws TaskAlreadyExistsException;
	Task save(Task task) throws TaskDoesntExistException;
	void delete(Long id);
}
