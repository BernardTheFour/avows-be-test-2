package com.betest.avows.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betest.avows.dtos.DepartmentDto;
import com.betest.avows.dtos.DepartmentAssigningDto;
import com.betest.avows.kafka.KafkaProducer;
import com.betest.avows.kafka.KafkaTopic.TopicEnum;
import com.betest.avows.models.Department;
import com.betest.avows.models.Contact;
import com.betest.avows.repositories.DepartmentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ContactService contactService;

    private final KafkaProducer kafkaProducer;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            ContactService contactService,
            KafkaProducer kafkaProducer) {
        this.departmentRepository = departmentRepository;
        this.contactService = contactService;
        this.kafkaProducer = kafkaProducer;
    }

    public Department saveDepartment(DepartmentDto departmentDto) {
        Department entity = new Department(departmentDto.name());
        Department savedEntity = departmentRepository.save(entity);

        kafkaProducer.sendMessage(TopicEnum.DEPARTMENT, savedEntity);
        return savedEntity;
    }

    public Department getDepartmentById(UUID id) {
        Department entity = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        return entity;
    }

    public List<Department> getAll() {
        List<Department> entities = departmentRepository.findAll();

        return entities;
    }

    public void departmentAssigning(UUID departmentId, DepartmentAssigningDto assigningDto) {
        Department department = getDepartmentById(departmentId);
        List<Contact> prevList = department.getContacts();

        List<Contact> nextList = assigningDto.contact_ids().stream()
                .map((id) -> contactService.getById(id))
                .collect(Collectors.toList());

        Set<Contact> mergedContact = new HashSet<>();
        mergedContact.addAll(prevList);
        mergedContact.addAll(nextList);

        mergedContact.stream()
                .forEach((contact) -> contact.setDepartment(department));

        // saving only works for 'one' relation side
        contactService.saveAllContacts(
                new ArrayList<>(mergedContact));
    }
}
