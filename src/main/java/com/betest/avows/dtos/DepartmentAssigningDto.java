package com.betest.avows.dtos;

import java.util.List;
import java.util.UUID;

public record DepartmentAssigningDto(
        List<UUID> contact_ids) {

}
