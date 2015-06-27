package com.croydon.quick.service;

import java.util.List;

import com.croydon.quick.domain.Project;
import com.croydon.quick.exception.ProjectAlreadyExistsException;

public interface ProjectService {

	List<Project> findAll();
	Project findOne(Long id);
	Project save(Project project) throws ProjectAlreadyExistsException;
	void delete(Long id);
}
