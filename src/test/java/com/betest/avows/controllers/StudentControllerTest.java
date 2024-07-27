package com.betest.avows.controllers;

import static org.mockito.Mockito.doReturn;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.betest.avows.dtos.ContactDto;
import com.betest.avows.models.Department;
import com.betest.avows.models.Contact;
import com.betest.avows.services.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {

    @MockBean
    private ContactService mockStudentService;

    @Autowired
    private MockMvc mockRequest;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("test: get student by their id")
    void testGetStudentById() throws Exception {
        // input
        UUID inputId = UUID.randomUUID();

        // stub result
        Contact student = new Contact(inputId, "00123", "name");
        Department classroom = new Department(UUID.randomUUID(), "classname");
        student.setDepartment(classroom);
        classroom.setContacts(List.of(student));
        doReturn(student)
                .when(mockStudentService)
                .getById(inputId);

        // test
        ContactDto resultDto = ContactDto.toDto(student);

        URI uri = new URI("/student/id/" + inputId);
        mockRequest
                .perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(resultDto)));
    }
}
