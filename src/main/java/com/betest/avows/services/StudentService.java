package com.betest.avows.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.betest.avows.dtos.ContactDto;
import com.betest.avows.models.Contact;
import com.betest.avows.repositories.ContactRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {

    private final ContactRepository studentRepository;

    public StudentService(ContactRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Contact saveStudent(ContactDto studentDto) {
        Contact entity = new Contact(studentDto.name(), studentDto.phone_number());
        return studentRepository.save(entity);
    }

    public List<Contact> saveAllStudent(List<Contact> entities) {
        return studentRepository.saveAll(entities);
    }

    public Contact getById(UUID id) {
        Contact entity = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return entity;
    }

    public List<Contact> getAll() {
        List<Contact> entities = studentRepository.findAll();

        return entities;
    }
}
