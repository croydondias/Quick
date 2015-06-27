package com.croydon.quick.service;

import java.util.List;

import com.croydon.quick.domain.Category;
import com.croydon.quick.exception.CategoryAlreadyExistsException;

public interface CategoryService {

	List<Category> findAll();
	Category findOne(Long id);
	Category save(Category category) throws CategoryAlreadyExistsException;
	void delete(Long id);
}
