package com.croydon.quick.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.exception.EmployeeAlreadyExistsException;
import com.croydon.quick.service.EmployeeService;

@RestController
@RequestMapping("/login")
public class LoginRestController {
	
	private static final Logger LOG = Logger.getLogger(LoginRestController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@RequestMapping(method = RequestMethod.POST, value = "/process")
//	public @ResponseBody Employee process(HttpServletRequest request, HttpServletResponse response) throws IllegalArgumentException {
//		final String email = request.getParameter("email");
//		final String password = request.getParameter("password");
//		
//		if (email == null || email.isEmpty()) {
//			throw new IllegalArgumentException("Email cannot be blank");
//		}
//		if (password == null || password.isEmpty()) {
//			throw new IllegalArgumentException("Password cannot be blank");
//		}
//		
//		boolean authResult = AuthenticatedWebSession.get().signIn(email, password);
//		LOG.info(String.format("Checking login: %s %s -> %b", email, password, authResult));
//		
//		return null;
//	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public @ResponseBody Employee register(HttpServletRequest request, HttpServletResponse response) throws IllegalArgumentException, EmployeeAlreadyExistsException {
		LOG.info("Registering user ...");
		
		Employee employee = new Employee();
		employee.setFirstName(request.getParameter("firstName"));
		employee.setLastName(request.getParameter("lastName"));
		employee.setEmail(request.getParameter("email"));
		employee.setPassword(request.getParameter("password"));
		
		if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
			throw new IllegalArgumentException("First name cannot be blank");
		}
		if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
			throw new IllegalArgumentException("Last name cannot be blank");
		}
		if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Email cannot be blank");
		}
		if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Password cannot be blank");
		}
		
		if (employeeService.findByEmail(employee.getEmail()) != null) {
			throw new EmployeeAlreadyExistsException("That email already exists.");
		}
		
		String encoded = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(encoded);
		
		return employeeService.create(employee);
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException e) {
	    return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.CONFLICT)
	public String handleIllegalArgumentException(IllegalArgumentException e) {
	    return e.getMessage();
	}
}
