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
import com.fasterxml.uuid.Generators;

@SpringBootTest()
@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository mockContactRepository;

    private ContactService contactService;

    @BeforeEach
    public void setup() {
        contactService = new ContactService(mockContactRepository);
    }

    @Test
    @DisplayName("test: save new contact success")
    void testSaveNewContactSuccessWhenValid() {
        // stub entity
        Contact mockEntity = new Contact(
                Generators.timeBasedEpochGenerator().generate(),
                "001122",
                "Name");
        doReturn(mockEntity)
                .when(mockContactRepository)
                .save(any(Contact.class));

        // input
        ContactDto contactDto = ContactDto.toDtoDetached(mockEntity);

        // test
        Contact expectedValue = mockEntity;
        Contact actualValue = contactService.saveContact(contactDto);

        assertAll(() -> {
            assertEquals(contactDto.name(), mockEntity.getName());
            assertEquals(contactDto.phone_number(), mockEntity.getPhoneNumber());
        });
        assertEquals(expectedValue, actualValue);
        verify(mockContactRepository, atMostOnce()).save(any(Contact.class));
    }

    @Test
    @DisplayName("test: save new contact failed when exception thrown")
    void testSaveNewContactFailedWhenExceptionThrown() {
        // stub entity
        Contact mockEntity = new Contact(
                Generators.timeBasedEpochGenerator().generate(),
                "001122",
                "Name");
        doThrow(DuplicateKeyException.class)
                .when(mockContactRepository)
                .save(any(Contact.class));

        // input
        ContactDto contactDto = ContactDto.toDtoDetached(mockEntity);

        // test
        assertThrows(DuplicateKeyException.class,
                () -> contactService.saveContact(contactDto));
    }

    @Test
    @DisplayName("test: get contact by id success when valid")
    void testGetContactByIdSuccessWhenValid() {
        UUID contactId = Generators.timeBasedEpochGenerator().generate();
        // stub entity
        Contact mockEntity = new Contact(
                contactId,
                "001122",
                "Name");
        doReturn(Optional.of(mockEntity))
                .when(mockContactRepository)
                .findById(contactId);

        // test
        Contact expectedResult = mockEntity;
        Contact actualResutl = contactService.getById(contactId);

        assertEquals(expectedResult, actualResutl);
        verify(mockContactRepository, atMostOnce()).findById(contactId);
    }
}
