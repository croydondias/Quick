package com.croydon.quick.config;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.croydon.quick.domain.Employee;
import com.croydon.quick.domain.EmployeeRepository;
import com.croydon.quick.service.EmployeeService;

public class BasicAuthenticationSession extends AuthenticatedWebSession {
	
	private static final Logger LOG = Logger.getLogger(BasicAuthenticationSession.class);
	
	@SpringBean
	private PasswordEncoder passwordEncoder;
	@SpringBean
	private EmployeeService employeeService;
	
	public BasicAuthenticationSession(Request request) {
		super(request);
		Injector.get().inject(this);
	}

	@Override
	public boolean authenticate(String username, String password) {
		final String email = username;
		Employee employee = employeeService.findByEmail(email);
		if (employee == null) {
			return false;
		}
		return passwordEncoder.matches(password, employee.getPassword());
	}

	@Override
	public Roles getRoles() {
		return null;
	}
}
