package com.company.repository;

import com.company.entity.CategoryEntity;
import com.company.enums.CategoryStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {
    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findByNameAndStatusAndVisible(String name, CategoryStatus status, Boolean visible);

}
