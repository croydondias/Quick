package com.croydon.quick.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

}

//@RepositoryRestResource(collectionResourceRel = "task", path = "task", exported = true)
//public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
//
//}
