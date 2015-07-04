package com.croydon.quick.service;

import java.util.List;

import com.croydon.quick.domain.Project;
import com.croydon.quick.exception.ProjectAlreadyExistsException;
import com.croydon.quick.exception.ProjectDoesntExistException;

public interface ProjectService {

	List<Project> findAll();
	Project findOne(Long id);
	Project create(Project project) throws ProjectAlreadyExistsException;
	Project save(Project project) throws ProjectDoesntExistException;
	void delete(Long id);
	Long remainingTaskCount(Long id);
}
