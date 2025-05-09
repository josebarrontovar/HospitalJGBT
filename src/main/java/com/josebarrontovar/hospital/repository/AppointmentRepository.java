package com.josebarrontovar.hospital.repository;

import com.josebarrontovar.hospital.model.Appointment;
import com.josebarrontovar.hospital.model.Clinic;
import com.josebarrontovar.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByClinicAndAppointmentTime(Clinic clinic, LocalDateTime appointmentTime);
    boolean existsByDoctorAndAppointmentTime(Doctor doctor, LocalDateTime appointmentTime);
    //List<Appointment> findByPatientNameAndAppointmentTimeBetween(String patientName, LocalDateTime startTime, LocalDateTime endTime);
    //List<Appointment> findByDoctorAndAppointmentTimeBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);
    long countByDoctorAndAppointmentTimeBetween(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime);
    public List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
    //List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);

}
