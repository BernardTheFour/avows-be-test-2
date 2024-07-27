package com.betest.avows.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betest.avows.dtos.ContactDto;
import com.betest.avows.models.Contact;
import com.betest.avows.services.ContactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final ContactService studentService;

    public StudentController(ContactService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<ContactDto> getStudentById(@PathVariable(name = "uuid") UUID uuid) {
        Contact studentEntity = studentService.getById(uuid);
        ContactDto studentDto = ContactDto.toDto(studentEntity);

        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("")
    public ResponseEntity<List<ContactDto>> getAllStudent() {
        List<Contact> studentEntities = studentService.getAll();
        List<ContactDto> studentDtos = studentEntities.stream()
                .map(ContactDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(studentDtos);
    }
}
