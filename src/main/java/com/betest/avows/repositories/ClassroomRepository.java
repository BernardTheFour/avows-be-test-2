package com.betest.avows.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.betest.avows.models.Department;

@Repository
public interface ClassroomRepository extends ListCrudRepository<Department, UUID> {
    
}
