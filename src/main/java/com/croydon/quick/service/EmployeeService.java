package com.croydon.quick.service;

import java.util.List;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.exception.EmployeeAlreadyExistsException;

public interface EmployeeService {

	List<Employee> findAll();
	Employee findOne(Long id);
	Employee save(Employee employee) throws EmployeeAlreadyExistsException;
	void delete(Long id);
}
