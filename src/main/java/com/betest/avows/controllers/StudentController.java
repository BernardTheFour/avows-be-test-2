package com.betest.avows.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betest.avows.dtos.StudentDto;
import com.betest.avows.models.Contact;
import com.betest.avows.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable(name = "uuid") UUID uuid) {
        Contact studentEntity = studentService.getById(uuid);
        StudentDto studentDto = StudentDto.toDto(studentEntity);

        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("")
    public ResponseEntity<List<StudentDto>> getAllStudent() {
        List<Contact> studentEntities = studentService.getAll();
        List<StudentDto> studentDtos = studentEntities.stream()
                .map(StudentDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentDtos);
    }
}
