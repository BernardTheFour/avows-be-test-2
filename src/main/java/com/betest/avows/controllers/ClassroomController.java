package com.betest.avows.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.betest.avows.dtos.DepartmentDto;
import com.betest.avows.dtos.DepartmentAssigningDto;
import com.betest.avows.models.Department;
import com.betest.avows.services.DepartmentService;

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

    private final DepartmentService classroomService;

    public ClassroomController(DepartmentService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping("")
    public ResponseEntity<DepartmentDto> saveNewClassroom(@RequestBody DepartmentDto dto) {
        Department classroomEntity = classroomService.saveDepartment(dto);
        DepartmentDto classroomDto = DepartmentDto.toDto(classroomEntity);

        return ResponseEntity.ok(classroomDto);
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<DepartmentDto> getClassroomById(@PathVariable(name = "uuid") UUID uuid) {
        Department classroomEntity = classroomService.getDepartmentById(uuid);
        DepartmentDto classroomDto = DepartmentDto.toDto(classroomEntity);

        return ResponseEntity.ok(classroomDto);
    }

    @GetMapping("")
    public ResponseEntity<List<DepartmentDto>> getAllClassroom() {
        List<Department> classroomEntities = classroomService.getAll();
        List<DepartmentDto> classroomDtos = classroomEntities.stream()
                .map(DepartmentDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(classroomDtos);
    }

    @PutMapping("/id/{uuid}/enroll")
    public ResponseEntity<String> classroomEnrollment(
            @PathVariable(name = "uuid") UUID uuid,
            @RequestBody DepartmentAssigningDto enrollmentDto) {
        classroomService.departmentAssigning(uuid, enrollmentDto);

        return ResponseEntity.ok("Class enrollment success");
    }

}
