package com.betest.avows.dtos;

import java.util.Objects;
import java.util.UUID;

import com.betest.avows.models.Contact;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Pattern;

@JsonInclude(value = Include.NON_NULL)
public record ContactDto(
        UUID id,
        @Pattern(regexp = "\\d+", message = "Phone number, number only") String phone_number,
        String name,
        DepartmentDto department) {

    public static ContactDto toDtoDetached(Contact entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return new ContactDto(
                entity.getId(),
                entity.getPhoneNumber(),
                entity.getName(),
                null);
    }

    public static ContactDto toDto(Contact entity) {
        DepartmentDto departmentDto = DepartmentDto
                // when chaining dto, avoid circular json printing
                .toDtoDetached(entity.getDepartment());

        return new ContactDto(
                entity.getId(),
                entity.getPhoneNumber(),
                entity.getName(),
                departmentDto);
    }
}
