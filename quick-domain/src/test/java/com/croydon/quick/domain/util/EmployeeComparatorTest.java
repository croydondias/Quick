package com.croydon.quick.domain.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.croydon.quick.domain.Employee;

public class EmployeeComparatorTest {

	@Test
	public void checkSorting() {
		List<String> firstNames = Arrays.asList("Zoe", "Croydon", "Amber", "Travis", "Croydon", "Croydon", "Croydon", "croydon");
		List<String> lastNames = Arrays.asList("Dias", "Dias", "Dias", "Dias", "Davis", "Dzy", "Dias", "Dias");
		assertEquals(firstNames.size(), lastNames.size());
		
		List<Employee> employees = new ArrayList<>();
		for (int i = 0; i < firstNames.size(); i++) {
			Employee e = new Employee();
			e.setFirstName(firstNames.get(i));
			e.setLastName(lastNames.get(i));
			employees.add(e);
		}
		
		List<String> expectedResult = Arrays.asList("Amber Dias", "Croydon Davis", "Croydon Dias", "Croydon Dias", "croydon Dias", 
				"Croydon Dzy", "Travis Dias", "Zoe Dias");
		Collections.sort(employees, new EmployeeComparator());
		assertEquals(employees.size(), expectedResult.size());
		for (int i = 0; i < employees.size(); i++) {
			Employee e = employees.get(i);
			String expected = expectedResult.get(i);
			String actual = String.format("%s %s", e.getFirstName(), e.getLastName());
			assertEquals(expected, actual);
		}
	}
}
