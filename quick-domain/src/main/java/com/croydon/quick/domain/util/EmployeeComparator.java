package com.croydon.quick.domain.util;

import java.util.Comparator;

import com.croydon.quick.domain.Employee;

public class EmployeeComparator implements Comparator<Employee> {
    
	@Override
    public int compare(Employee o1, Employee o2) {
        int result = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
        
        if (result == 0) {
        	result = o1.getLastName().compareToIgnoreCase(o2.getLastName());
        }
        return result;
    }
}
