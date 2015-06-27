package com.croydon.quick.api;

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

import com.croydon.quick.domain.Project;
import com.croydon.quick.domain.Task;
import com.croydon.quick.exception.TaskAlreadyExistsException;
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
	public Task create(@RequestBody Task task) throws Exception {
		return taskService.save(task);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable String id) {
		taskService.delete(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public Task update(@PathVariable String id, @RequestBody Task task) {
		return null;
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleUserAlreadyExistsException(TaskAlreadyExistsException e) {
	    return e.getMessage();
	}
	
}
