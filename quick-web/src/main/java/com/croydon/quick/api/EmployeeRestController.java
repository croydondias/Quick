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

import com.croydon.quick.domain.Employee;
import com.croydon.quick.exception.EmployeeAlreadyExistsException;
import com.croydon.quick.exception.EmployeeDoesntExistException;
import com.croydon.quick.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Employee> getAll() {
		return employeeService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public Employee get(@PathVariable String id) {
		return employeeService.findOne(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.POST)
	public Employee create(@RequestBody Employee employee) throws EmployeeAlreadyExistsException {
		return employeeService.create(employee);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void delete(@PathVariable String id) {
		employeeService.delete(Long.valueOf(id));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public Employee update(@PathVariable String id, @RequestBody Employee employee) throws EmployeeDoesntExistException {
		Employee update = employeeService.findOne(Long.valueOf(id));
		update.setFirstName(employee.getFirstName());
		update.setLastName(employee.getLastName());
		update.setEmail(employee.getEmail());
		update.setPassword(employee.getPassword());  // TODO possible risk
		return employeeService.save(update);
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException e) {
	    return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleEmployeeDoesntExistException(EmployeeDoesntExistException e) {
	    return e.getMessage();
	}
	
}
