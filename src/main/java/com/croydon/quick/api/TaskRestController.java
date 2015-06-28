package com.croydon.quick.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.croydon.quick.domain.Task;
import com.croydon.quick.exception.TaskAlreadyExistsException;
import com.croydon.quick.exception.TaskDoesntExistException;
import com.croydon.quick.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

	@Autowired
	private TaskService taskService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Task> getAll() {
		return taskService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public Task get(@PathVariable String id) {
		return taskService.findOne(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.POST)
	public Task create(@RequestBody Task task) throws TaskAlreadyExistsException {
		return taskService.create(task);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable String id) {
		taskService.delete(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public Task update(@PathVariable String id, @RequestBody Task task) throws TaskDoesntExistException {
		Task update = taskService.findOne(Long.valueOf(id));
		update.setName(task.getName());
		update.setDescription(task.getDescription());
		update.setDone(task.getDone());
		update.setProject_id(task.getProject_id());
		update.setEmployee_id(task.getEmployee_id());
		return taskService.save(update);
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleTaskAlreadyExistsException(TaskAlreadyExistsException e) {
	    return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleTaskDoesntExistException(TaskDoesntExistException e) {
	    return e.getMessage();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findByProjectId/{id}")
	public List<Task> findByProjectId(@PathVariable String id) {
		final long projectId = Long.valueOf(id);
		List<Task> tasks = new ArrayList<>();
		for (Task task : getAll()) {
			if (task.getProject_id() == projectId) {
				tasks.add(task);
			}
		}
		return tasks.isEmpty() ? null : tasks;
	}
}
