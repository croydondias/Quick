package com.croydon.quick.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.exception.EmployeeAlreadyExistsException;
import com.croydon.quick.service.EmployeeService;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {
	
	private static final Logger LOG = Logger.getLogger(LoginRestController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
//	@RequestMapping(method = RequestMethod.POST, value = "/register")
//	public Employee register(@RequestBody Employee employee) throws EmployeeAlreadyExistsException {
//		LOG.info(String.format("Register: %s", employee));
//		
//		return null;
//		//return employeeService.create(user);
//	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public @ResponseBody Employee register(HttpServletRequest request, HttpServletResponse response) throws EmployeeAlreadyExistsException {
		LOG.info("Registering user ...");
		
		Employee employee = new Employee();
		employee.setFirstName(request.getParameter("firstName"));
		employee.setLastName(request.getParameter("lastName"));
		employee.setEmail(request.getParameter("email"));
		employee.setPassword(request.getParameter("password"));
		LOG.info("Form parmeters: " + employee);
		
		
		return employee;
		//return employeeService.create(user);
	}
}
