package com.croydon.quick.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

}

//@RepositoryRestResource(collectionResourceRel = "employee", path = "employee", exported = true)
//public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
//
//}