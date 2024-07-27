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
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("")
    public ResponseEntity<DepartmentDto> saveNewDepartment(@RequestBody DepartmentDto dto) {
        Department departmentEntity = departmentService.saveDepartment(dto);
        DepartmentDto departmentDto = DepartmentDto.toDto(departmentEntity);

        return ResponseEntity.ok(departmentDto);
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(name = "uuid") UUID uuid) {
        Department departmentEntity = departmentService.getDepartmentById(uuid);
        DepartmentDto departmentDto = DepartmentDto.toDto(departmentEntity);

        return ResponseEntity.ok(departmentDto);
    }

    @GetMapping("")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<Department> departmentEntities = departmentService.getAll();
        List<DepartmentDto> departmentDtos = departmentEntities.stream()
                .map(DepartmentDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(departmentDtos);
    }

    @PutMapping("/id/{uuid}/enroll")
    public ResponseEntity<String> departmentAssigning(
            @PathVariable(name = "uuid") UUID uuid,
            @RequestBody DepartmentAssigningDto departmentAssigningDto) {
        departmentService.departmentAssigning(uuid, departmentAssigningDto);

        return ResponseEntity.ok("Department assigning success");
    }

}
