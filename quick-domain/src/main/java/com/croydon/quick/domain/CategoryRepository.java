package com.croydon.quick.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

}

//@RepositoryRestResource(collectionResourceRel = "category", path = "category", exported = true)
//public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
//
//}