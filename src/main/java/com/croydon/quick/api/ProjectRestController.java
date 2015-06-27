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
import com.croydon.quick.exception.ProjectAlreadyExistsException;
import com.croydon.quick.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectRestController {

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Project> getAll() {
		return projectService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public Project get(@PathVariable String id) {
		return projectService.findOne(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.POST)
	public Project create(@RequestBody Project project) throws Exception {
		return projectService.save(project);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable String id) {
		projectService.delete(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public Project update(@PathVariable String id, @RequestBody Project project) {
		return null;
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleUserAlreadyExistsException(ProjectAlreadyExistsException e) {
	    return e.getMessage();
	}
	
}
