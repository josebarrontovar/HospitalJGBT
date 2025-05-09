package com.josebarrontovar.hospital.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer roomNumber;

    @Column(nullable = false)
    private Integer floor;

    @OneToMany(mappedBy = "clinic")
    private List<Appointment> appointments;
}
