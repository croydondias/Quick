package com.croydon.quick.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.croydon.quick.domain.Category;
import com.croydon.quick.domain.CategoryRepository;
import com.croydon.quick.exception.CategoryAlreadyExistsException;
import com.croydon.quick.exception.CategoryDoesntExistException;
import com.croydon.quick.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAll() {
		return (List<Category>) categoryRepository.findAll();
	}

	@Override
	public Category findOne(Long id) {
		return categoryRepository.findOne(id);
	}

	@Override
	@Transactional
	public Category create(Category category) throws CategoryAlreadyExistsException {
		Category existing = categoryRepository.findOne(category.getId());
	    if (existing != null) {
	        throw new CategoryAlreadyExistsException(
	                String.format("There already exists a category with id=%s", category.getId()));
	    }
	    return categoryRepository.save(category);
	}
	
	@Override
	@Transactional
	public Category save(Category category) throws CategoryDoesntExistException {
		Category existing = categoryRepository.findOne(category.getId());
	    if (existing == null) {
	        throw new CategoryDoesntExistException(
	                String.format("Category id=%s does not exist", category.getId()));
	    }
	    return categoryRepository.save(category);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		categoryRepository.delete(id);
	}

}
