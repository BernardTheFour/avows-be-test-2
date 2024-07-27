package com.betest.avows.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.betest.avows.models.Contact;

@Repository
public interface StudentRepository extends ListCrudRepository<Contact, UUID> {

}
