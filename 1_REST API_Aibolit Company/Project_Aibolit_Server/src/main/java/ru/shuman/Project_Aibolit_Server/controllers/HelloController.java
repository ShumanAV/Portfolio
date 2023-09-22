package ru.shuman.Project_Aibolit_Server.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.shuman.Project_Aibolit_Server.security.ProfileDetails;

@Controller
public class HelloController {

//    private final AdminService adminService;
//
//    @Autowired
//    public HelloController(AdminService adminService) {
//        this.adminService = adminService;
//    }
//
//    @GetMapping("/hello")
//    public String sayHello() {
//        return ("hello");
//    }

    // Получим из сессии данные аутентифицированного пользователя
    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ProfileDetails profileDetails = (ProfileDetails) authentication.getPrincipal();
        System.out.println(profileDetails.getProfile().getUsername());

        return profileDetails.getUsername();
    }

}
