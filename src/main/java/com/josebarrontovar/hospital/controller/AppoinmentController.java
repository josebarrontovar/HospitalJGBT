package com.josebarrontovar.hospital.controller;

import com.josebarrontovar.hospital.model.Appointment;
import com.josebarrontovar.hospital.model.Doctor;
import com.josebarrontovar.hospital.repository.ClinicRepository;
import com.josebarrontovar.hospital.repository.DoctorRepository;
import com.josebarrontovar.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppoinmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @GetMapping("/new")
    public String showAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("clinics", clinicRepository.findAll());
        return "appointments/form";
    }

    @PostMapping("/create")
    public String createAppointment(@ModelAttribute Appointment appointment, Model model) {
        try {

            if (appointment.getAppointmentTime() == null || appointment.getPatientName() == null || appointment.getPatientName().isEmpty()) {
                model.addAttribute("error", "Appointment time and nname are required.");
                return "appointments/form";
            }

            appointmentService.createAppointment(appointment);
            model.addAttribute("appointment", new Appointment());
            model.addAttribute("doctors", doctorRepository.findAll());
            model.addAttribute("clinics", clinicRepository.findAll());
            model.addAttribute("success", true);
            return "appointments/form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "exist appointment in this date.");
            return "redirect:/appointments/error";
        }
    }

    @GetMapping("/error")
    public String showErrorPage(Model model) {
        return "appointments/error";
    }

    @GetMapping("/list")
    public String listAppointments(@RequestParam(required = false) Long doctorId, Model model) {
        List<Appointment> appointments;

        if (doctorId != null) {
            appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        } else {
            appointments = appointmentService.getAppointmentsByDoctorAndDate(null, LocalDate.now());
        }

        model.addAttribute("appointments", appointments);
        model.addAttribute("doctorId", doctorId);
        return "appointments/list";
    }

    @GetMapping("/appointments/{doctorId}")
    public String listAppointmentsForDoctor(@PathVariable Long doctorId, Model model) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        model.addAttribute("appointments", appointments);
        model.addAttribute("doctorId", doctorId);
        return "appointments/list";
    }


    @GetMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return "redirect:/appointments/list";
    }


    @GetMapping("/edit/{id}")
    public String editAppointment(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("clinics", clinicRepository.findAll());
        return "appointments/form";
    }

    @GetMapping("/doctors")
    public String listDoctors(@RequestParam(required = false) Long doctorId,
                              @RequestParam(required = false) String appointmentDate,
                              Model model) {
        List<Doctor> doctors = doctorRepository.findAll();

        List<Appointment> appointments = null;

        if (doctorId != null || appointmentDate != null) {
            LocalDate date = null;

            if (appointmentDate != null) {
                try {
                    date = LocalDate.parse(appointmentDate);
                } catch (Exception e) {
                    model.addAttribute("error", "formato invalido de date.");
                    date = LocalDate.now();
                }
            }

            appointments = appointmentService.getAppointmentsByDoctorAndDate(doctorId, date);
        } else {

            appointments = appointmentService.getAppointmentsByDoctor(null);
        }

        model.addAttribute("appointments", appointments);
        model.addAttribute("doctors", doctors);
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("appointmentDate", appointmentDate);

        return "appointments/doctors";
    }
}
