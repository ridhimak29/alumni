package com.manifesters.alumni.controller;

import com.manifesters.alumni.dao.CustomUserDetails;
import com.manifesters.alumni.dao.User;
import com.manifesters.alumni.dao.UserRepository;
import com.manifesters.alumni.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AppController {
    @Autowired
    UserRepository userRepo;

    @Autowired
    UserSessionService userSessionService;

    @GetMapping("")
    public String viewHomePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth", auth);
        System.out.println("user session Id - " + userSessionService.getUserSessionId());
        model.addAttribute("sessionId", userSessionService.getUserSessionId());
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //((WebAuthenticationDetails)((UsernamePasswordAuthenticationToken)auth).details).sessionId
        return "home";
    }

    @GetMapping("/sessionTest")
    public String getSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal().equals("anonymousUser")) {
            System.out.println("User not Authneticated");
            return "home";
        } else {
            System.out.println("User Authneticated");
            return "homeAfterLogin";
        }
    }

}
