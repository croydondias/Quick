package com.croydon.quick.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.croydon.quick.domain.Project;
import com.croydon.quick.domain.ProjectRepository;
import com.croydon.quick.exception.ProjectAlreadyExistsException;
import com.croydon.quick.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public List<Project> findAll() {
		return (List<Project>) projectRepository.findAll();
	}

	@Override
	public Project findOne(Long id) {
		return projectRepository.findOne(id);
	}

	@Override
	@Transactional
	public Project save(Project project) throws ProjectAlreadyExistsException {
		Project existing = projectRepository.findOne(project.getId());
	    if (existing != null) {
	        throw new ProjectAlreadyExistsException(
	                String.format("There already exists a project with id=%s", project.getId()));
	    }
	    return projectRepository.save(project);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		projectRepository.delete(id);
	}

}