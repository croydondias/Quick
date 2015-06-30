package com.croydon.quick.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.croydon.quick.domain.Task;
import com.croydon.quick.domain.TaskRepository;
import com.croydon.quick.exception.TaskAlreadyExistsException;
import com.croydon.quick.exception.TaskDoesntExistException;
import com.croydon.quick.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public List<Task> findAll() {
		return (List<Task>) taskRepository.findAll();
	}

	@Override
	public Task findOne(Long id) {
		return taskRepository.findOne(id);
	}
	
	@Override
	@Transactional
	public Task create(Task task) throws TaskAlreadyExistsException {
		if (task.getId() != null) {
			Task existing = taskRepository.findOne(task.getId());
		    if (existing != null) {
		        throw new TaskAlreadyExistsException(
		                String.format("There already exists a task with id=%s", task.getId()));
		    }
		}
	    return taskRepository.save(task);
	}
	
	@Override
	@Transactional
	public Task save(Task task) throws TaskDoesntExistException {
		Task existing = taskRepository.findOne(task.getId());
	    if (existing == null) {
	        throw new TaskDoesntExistException(
	                String.format("Task id=%s does not exist", task.getId()));
	    }
	    return taskRepository.save(task);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		taskRepository.delete(id);
	}

}
