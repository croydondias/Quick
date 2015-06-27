package com.croydon.quick.service;

import java.util.List;

import com.croydon.quick.domain.Category;
import com.croydon.quick.exception.CategoryAlreadyExistsException;
import com.croydon.quick.exception.CategoryDoesntExistException;

public interface CategoryService {

	List<Category> findAll();
	Category findOne(Long id);
	Category create(Category category) throws CategoryAlreadyExistsException;
	Category save(Category category) throws CategoryDoesntExistException;
	void delete(Long id);
}
