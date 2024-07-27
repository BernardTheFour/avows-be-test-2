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
public class ClassroomService {
    private final DepartmentRepository classroomRepository;
    private final StudentService studentService;

    private final KafkaProducer kafkaProducer;

    public ClassroomService(
            DepartmentRepository classroomRepository,
            StudentService studentService,
            KafkaProducer kafkaProducer) {
        this.classroomRepository = classroomRepository;
        this.studentService = studentService;
        this.kafkaProducer = kafkaProducer;
    }

    public Department saveClassroom(DepartmentDto classroomDto) {
        Department entity = new Department(classroomDto.name());
        Department savedEntity = classroomRepository.save(entity);

        kafkaProducer.sendMessage(TopicEnum.CLASSROOM, savedEntity);
        return savedEntity;
    }

    public Department getClassroomById(UUID id) {
        Department entity = classroomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found"));

        return entity;
    }

    public List<Department> getAll() {
        List<Department> entities = classroomRepository.findAll();

        return entities;
    }

    public void classroomEnrollment(UUID classroomId, DepartmentAssigningDto enrollmentDto) {
        Department classroom = getClassroomById(classroomId);
        List<Contact> prevList = classroom.getContacts();

        List<Contact> nextList = enrollmentDto.contact_ids().stream()
                .map((id) -> studentService.getById(id))
                .collect(Collectors.toList());

        Set<Contact> mergedStudent = new HashSet<>();
        mergedStudent.addAll(prevList);
        mergedStudent.addAll(nextList);

        mergedStudent.stream()
                .forEach((student) -> student.setDepartment(classroom));

        // saving only works for 'one' relation side
        studentService.saveAllStudent(
                new ArrayList<>(mergedStudent));
    }
}
