package com.example.Project_2_Library_Spring.Boot_Lesson_74.controllers;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.services.AdminService;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;

@Controller
public class AdminController {

    private final PersonService personService;
    private final AdminService adminService;

    @Autowired
    public AdminController(PersonService personService, AdminService adminService) {
        this.personService = personService;
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String adminPage(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("admins_people", personService.findByRole("ROLE_USER"));
        model.addAttribute("users_people", personService.findByRole("ROLE_ADMIN"));
        return "/admin";
    }

    @PatchMapping("/user_to_admin")
    public String assignAdmin(@ModelAttribute("person") Person selectedPerson) {
        // у selectedPerson назначено только поле id остальные null
        adminService.setRole(selectedPerson, "ROLE_ADMIN");
        return "/index";
    }

    @PatchMapping("/admin_to_user")
    public String assignUser(@ModelAttribute("person") Person selectedPerson) {
        // у selectedPerson назначено только поле id остальные null
        adminService.setRole(selectedPerson, "ROLE_USER");
        return "/index";
    }
}
