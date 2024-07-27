package com.betest.avows.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betest.avows.dtos.ContactDto;
import com.betest.avows.models.Contact;
import com.betest.avows.services.ContactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable(name = "uuid") UUID uuid) {
        Contact contactEntity = contactService.getById(uuid);
        ContactDto contactDto = ContactDto.toDto(contactEntity);

        return ResponseEntity.ok(contactDto);
    }

    @GetMapping("")
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        List<Contact> contactEntities = contactService.getAll();
        List<ContactDto> contactDtos = contactEntities.stream()
                .map(ContactDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(contactDtos);
    }
}
