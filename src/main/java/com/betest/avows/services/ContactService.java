package com.betest.avows.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.betest.avows.dtos.ContactDto;
import com.betest.avows.models.Contact;
import com.betest.avows.repositories.ContactRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact saveContact(ContactDto contactDto) {
        Contact entity = new Contact(contactDto.name(), contactDto.phone_number());
        return contactRepository.save(entity);
    }

    public List<Contact> saveAllContacts(List<Contact> entities) {
        return contactRepository.saveAll(entities);
    }

    public Contact getById(UUID id) {
        Contact entity = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));

        return entity;
    }

    public List<Contact> getAll() {
        List<Contact> entities = contactRepository.findAll();

        return entities;
    }
}
