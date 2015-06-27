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
import com.croydon.quick.exception.ProjectDoesntExistException;
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
	public Project create(@RequestBody Project project) throws ProjectAlreadyExistsException {
		return projectService.create(project);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable String id) {
		projectService.delete(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public Project update(@PathVariable String id, @RequestBody Project project) throws ProjectDoesntExistException {
		Project update = projectService.findOne(Long.valueOf(id));
		update.setName(project.getName());
		update.setDescription(project.getDescription());
		update.setCategory_id(project.getCategory_id());
		return projectService.save(update);
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleProjectAlreadyExistsException(ProjectAlreadyExistsException e) {
	    return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleProjectDoesntExistException(ProjectDoesntExistException e) {
	    return e.getMessage();
	}
}
