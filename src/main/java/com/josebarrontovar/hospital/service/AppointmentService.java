package com.josebarrontovar.hospital.service;

import com.josebarrontovar.hospital.model.Appointment;
import com.josebarrontovar.hospital.repository.AppointmentRepository;
import com.josebarrontovar.hospital.repository.ClinicRepository;
import com.josebarrontovar.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public void createAppointment(Appointment appointment) {

        if (appointmentRepository.existsByClinicAndAppointmentTime(appointment.getClinic(), appointment.getAppointmentTime())) {
            throw new IllegalArgumentException("Ya existe una cita en este tiempo");
        }
        if (appointmentRepository.existsByDoctorAndAppointmentTime(appointment.getDoctor(), appointment.getAppointmentTime())) {
            throw new IllegalArgumentException("El doctor ya tiene una cita en este tiempo.");
        }

        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        if (doctorId != null && date != null) {
            return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                    doctorId,
                    date.atStartOfDay(),
                    date.atTime(23, 59, 59)
            );
        } else {
            return appointmentRepository.findAll();
        }
    }

    public void cancelAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("error del id: " + id));
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        if (doctorId == null) {
            return appointmentRepository.findAll();
        }


        return appointmentRepository.findByDoctorId(doctorId);
    }
}
