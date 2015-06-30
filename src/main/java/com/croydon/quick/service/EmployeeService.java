package com.croydon.quick.service;

import java.util.List;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.exception.EmployeeAlreadyExistsException;
import com.croydon.quick.exception.EmployeeDoesntExistException;

public interface EmployeeService {

	List<Employee> findAll();
	Employee findOne(Long id);
	Employee create(Employee employee) throws EmployeeAlreadyExistsException;
	Employee save(Employee employee) throws EmployeeDoesntExistException;
	void delete(Long id);
	List<Employee> findByEmail(String email);
}
