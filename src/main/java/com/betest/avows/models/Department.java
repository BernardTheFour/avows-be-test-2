package com.betest.avows.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.uuid.Generators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(unique = true, name = "id")
    UUID id = Generators.timeBasedEpochGenerator().generate();

    @Column(unique = true, name = "name")
    String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    List<Contact> contacts = new ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
