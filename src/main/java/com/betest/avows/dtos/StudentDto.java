package com.betest.avows.dtos;

import java.util.Objects;
import java.util.UUID;

import com.betest.avows.models.Contact;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Pattern;

@JsonInclude(value = Include.NON_NULL)
public record StudentDto(
        UUID id,
        @Pattern(regexp = "\\d+", message = "NISN number only") String nisn,
        String name,
        ClassroomDto classroom) {

    public static StudentDto toDtoDetached(Contact entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return new StudentDto(
                entity.getId(),
                entity.getPhoneNumber(),
                entity.getName(),
                null);
    }

    public static StudentDto toDto(Contact entity) {
        ClassroomDto classroomDto = ClassroomDto
                // when chaining dto, avoid circular json printing
                .toDtoDetached(entity.getDepartment());

        return new StudentDto(
                entity.getId(),
                entity.getPhoneNumber(),
                entity.getName(),
                classroomDto);
    }
}
