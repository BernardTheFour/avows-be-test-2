package com.betest.avows.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import com.betest.avows.dtos.ContactDto;
import com.betest.avows.models.Contact;
import com.betest.avows.repositories.ContactRepository;

@SpringBootTest()
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private ContactRepository mockStudentRepository;

    private ContactService studentService;

    @BeforeEach
    public void setup() {
        studentService = new ContactService(mockStudentRepository);
    }

    @Test
    @DisplayName("test: save new student success")
    void testSaveNewStudentSuccessWhenValid() {
        // stub entity
        Contact mockEntity = new Contact(
                UUID.randomUUID(),
                "001122",
                "Name");
        doReturn(mockEntity)
                .when(mockStudentRepository)
                .save(any(Contact.class));

        // input
        ContactDto studentDto = ContactDto.toDtoDetached(mockEntity);

        // test
        Contact expectedValue = mockEntity;
        Contact actualValue = studentService.saveContact(studentDto);

        assertAll(() -> {
            assertEquals(studentDto.name(), mockEntity.getName());
            assertEquals(studentDto.phone_number(), mockEntity.getPhoneNumber());
        });
        assertEquals(expectedValue, actualValue);
        verify(mockStudentRepository, atMostOnce()).save(any(Contact.class));
    }

    @Test
    @DisplayName("test: save new student failed when exception thrown")
    void testSaveNewStudenFailedWhenExceptionThrown() {
        // stub entity
        Contact mockEntity = new Contact(
                UUID.randomUUID(),
                "001122",
                "Name");
        doThrow(DuplicateKeyException.class)
                .when(mockStudentRepository)
                .save(any(Contact.class));

        // input
        ContactDto studentDto = ContactDto.toDtoDetached(mockEntity);

        // test
        assertThrows(DuplicateKeyException.class,
                () -> studentService.saveContact(studentDto));
    }

    @Test
    @DisplayName("test: get student by id success when valid")
    void testGetStudentByIdSuccessWhenValid() {
        UUID studentId = UUID.randomUUID();
        // stub entity
        Contact mockEntity = new Contact(
                studentId,
                "001122",
                "Name");
        doReturn(Optional.of(mockEntity))
                .when(mockStudentRepository)
                .findById(studentId);

        // test
        Contact expectedResult = mockEntity;
        Contact actualResutl = studentService.getById(studentId);

        assertEquals(expectedResult, actualResutl);
        verify(mockStudentRepository, atMostOnce()).findById(studentId);
    }
}
