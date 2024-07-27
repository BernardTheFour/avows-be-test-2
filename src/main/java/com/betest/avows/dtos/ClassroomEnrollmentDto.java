package com.betest.avows.dtos;

import java.util.List;
import java.util.UUID;

public record ClassroomEnrollmentDto(
        List<UUID> student_ids) {

}
