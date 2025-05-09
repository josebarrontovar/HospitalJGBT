package com.josebarrontovar.hospital.service;

import com.josebarrontovar.hospital.model.Clinic;
import com.josebarrontovar.hospital.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public List<Clinic> getAllRooms() {
        return clinicRepository.findAll();
    }

    public Clinic saveRoom(Clinic room) {
        return clinicRepository.save(room);
    }
}