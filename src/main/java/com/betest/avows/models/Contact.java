package com.betest.avows.models;

import java.util.UUID;

import com.fasterxml.uuid.Generators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @Column(unique = true, name = "id")
    UUID id = Generators.timeBasedEpochGenerator().generate();

    @Column(unique = true, name = "phone_number")
    @Pattern(regexp = "\\d+", message = "Phone number must consist of numbers only")
    String phoneNumber;

    @Column(name = "name")
    String name;

    @ManyToOne()
    @JoinColumn(name = "department_id")
    Department department;

    public Contact() {
    }

    public Contact(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public Contact(UUID id, String phoneNumber, String name) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
