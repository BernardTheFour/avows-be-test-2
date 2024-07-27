package com.betest.avows.dtos;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.betest.avows.models.Department;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public record DepartmentDto(
        UUID id,
        String name,
        List<ContactDto> contacts) {

    public static DepartmentDto toDtoDetached(Department entity) {
        if (Objects.isNull(entity)){
            return null;
        }

        return new DepartmentDto(
                entity.getId(),
                entity.getName(),
                null);
    }

    public static DepartmentDto toDto(Department entity) {
        List<ContactDto> contactDtos = entity.getContacts()
                .stream()
                // when chaining dto, avoid circular json printing
                .map(ContactDto::toDtoDetached)
                .collect(Collectors.toList());

        return new DepartmentDto(
                entity.getId(),
                entity.getName(),
                contactDtos);
    }
}
