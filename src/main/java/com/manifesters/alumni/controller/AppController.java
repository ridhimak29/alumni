package com.manifesters.alumni.controller;

import com.manifesters.alumni.types.User;
import com.manifesters.alumni.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    @Autowired
    UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "signup_form";
    }
    @PostMapping("/process_register")
    public  String processRegistration(User user) {
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode((user.getPassword()));
        user.setPassword(encodedPassword);
        userRepo.save(user);

        return "register_success";
    }

    @GetMapping("/home")
    public String homeView() {
        return "home";
    }


}
