package com.croydon.quick.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.domain.EmployeeRepository;
import com.croydon.quick.exception.EmployeeAlreadyExistsException;
import com.croydon.quick.exception.EmployeeDoesntExistException;
import com.croydon.quick.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> findAll() {
		return (List<Employee>) employeeRepository.findAll();
	}

	@Override
	public Employee findOne(Long id) {
		return employeeRepository.findOne(id);
	}

	@Override
	@Transactional
	public Employee create(Employee employee) throws EmployeeAlreadyExistsException {
		if (employee.getId() != null) {
			Employee existing = employeeRepository.findOne(employee.getId());
		    if (existing != null) {
		        throw new EmployeeAlreadyExistsException(
		                String.format("There already exists an employee with id=%s", employee.getId()));
		    }
		}
	    return employeeRepository.save(employee);
	}
	
	@Override
	@Transactional
	public Employee save(Employee employee) throws EmployeeDoesntExistException {
		Employee existing = employeeRepository.findOne(employee.getId());
	    if (existing == null) {
	        throw new EmployeeDoesntExistException(
	                String.format("Employee id=%s does not exist", employee.getId()));
	    }
	    return employeeRepository.save(employee);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		employeeRepository.delete(id);
	}
	
	@Override
	public Employee findByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}
}
