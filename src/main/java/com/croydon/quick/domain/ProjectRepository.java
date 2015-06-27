package com.croydon.quick.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {

}

//@RepositoryRestResource(collectionResourceRel = "project", path = "project", exported = true)
//public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
//
//}