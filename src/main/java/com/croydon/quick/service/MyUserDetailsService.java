package com.croydon.quick.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.croydon.quick.controller.LoginRestController;
import com.croydon.quick.domain.Employee;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	
	private static final Logger LOG = Logger.getLogger(LoginRestController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		
		LOG.info(email + " is being loaded");
		
		Employee employee = employeeService.findByEmail(email);
		
		List<GrantedAuthority> authorities = buildUserAuthority((Set<String>) Arrays.asList("ROLE"));
 
		return buildUserForAuthentication(employee, authorities);
	}
	
	private User buildUserForAuthentication(Employee user, List<GrantedAuthority> authorities) {
		final boolean isEnabled = true;
		return new User(user.getEmail(), user.getPassword(), 
			isEnabled, true, true, true, authorities);
	}
	 
	private List<GrantedAuthority> buildUserAuthority(Set<String> userRoles) {
 
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
 
		// Build user's authorities
		for (String userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole));
		}
 
		return new ArrayList<GrantedAuthority>(setAuths);
	}

}
