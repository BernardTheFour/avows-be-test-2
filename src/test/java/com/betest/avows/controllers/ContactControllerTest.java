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
import com.fasterxml.uuid.Generators;

@WebMvcTest(controllers = ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContactControllerTest {

    @MockBean
    private ContactService mockContactService;

    @Autowired
    private MockMvc mockRequest;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("test: get contact by their id")
    void testGetContactById() throws Exception {
        // input
        UUID inputId = Generators.timeBasedEpochGenerator().generate();

        // stub result
        Contact contact = new Contact(inputId, "00123", "name");
        Department department = new Department(Generators.timeBasedEpochGenerator().generate(), "department");
        contact.setDepartment(department);
        department.setContacts(List.of(contact));
        doReturn(contact)
                .when(mockContactService)
                .getById(inputId);

        // test
        ContactDto resultDto = ContactDto.toDto(contact);

        URI uri = new URI("/contact/id/" + inputId);
        mockRequest
                .perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(resultDto)));
    }
}
