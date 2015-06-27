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

import com.croydon.quick.domain.Category;
import com.croydon.quick.exception.CategoryAlreadyExistsException;
import com.croydon.quick.exception.CategoryDoesntExistException;
import com.croydon.quick.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Category> getAll() {
		return categoryService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public Category get(@PathVariable String id) {
		return categoryService.findOne(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.POST)
	public Category create(@RequestBody Category category) throws CategoryAlreadyExistsException {
		return categoryService.create(category);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable String id) {
		categoryService.delete(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public Category update(@PathVariable String id, @RequestBody Category category) throws CategoryDoesntExistException {
		Category update = categoryService.findOne(Long.valueOf(id));
		update.setName(category.getName());
		return categoryService.save(update);
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e) {
	    return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleCategoryDoesnExistException(CategoryDoesntExistException e) {
	    return e.getMessage();
	}
}
