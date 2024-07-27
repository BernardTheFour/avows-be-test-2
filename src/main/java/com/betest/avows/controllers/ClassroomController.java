package com.betest.avows.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.betest.avows.dtos.ClassroomDto;
import com.betest.avows.dtos.ClassroomEnrollmentDto;
import com.betest.avows.models.Department;
import com.betest.avows.services.ClassroomService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping("")
    public ResponseEntity<ClassroomDto> saveNewClassroom(@RequestBody ClassroomDto dto) {
        Department classroomEntity = classroomService.saveClassroom(dto);
        ClassroomDto classroomDto = ClassroomDto.toDto(classroomEntity);

        return ResponseEntity.ok(classroomDto);
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<ClassroomDto> getClassroomById(@PathVariable(name = "uuid") UUID uuid) {
        Department classroomEntity = classroomService.getClassroomById(uuid);
        ClassroomDto classroomDto = ClassroomDto.toDto(classroomEntity);

        return ResponseEntity.ok(classroomDto);
    }

    @GetMapping("")
    public ResponseEntity<List<ClassroomDto>> getAllClassroom() {
        List<Department> classroomEntities = classroomService.getAll();
        List<ClassroomDto> classroomDtos = classroomEntities.stream()
                .map(ClassroomDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(classroomDtos);
    }

    @PutMapping("/id/{uuid}/enroll")
    public ResponseEntity<String> classroomEnrollment(
            @PathVariable(name = "uuid") UUID uuid,
            @RequestBody ClassroomEnrollmentDto enrollmentDto) {
        classroomService.classroomEnrollment(uuid, enrollmentDto);

        return ResponseEntity.ok("Class enrollment success");
    }

}
