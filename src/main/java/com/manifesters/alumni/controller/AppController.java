package com.manifesters.alumni.controller;

import com.manifesters.alumni.dao.EventRepository;
import com.manifesters.alumni.types.Transaction;
import com.manifesters.alumni.types.User;
import com.manifesters.alumni.dao.UserRepository;
import com.manifesters.alumni.service.UserSessionService;
import com.manifesters.alumni.dao.TransactionRepository;
import com.manifesters.alumni.types.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class AppController {
    @Autowired
    UserRepository userRepo;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserSessionService userSessionService;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("")
    public String viewHomePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth", auth);
        System.out.println("user session Id - " + userSessionService.getUserSessionId());
        model.addAttribute("sessionId", userSessionService.getUserSessionId());
        return "index";

    }

    @GetMapping("/login")
    public String viewLoginPage() {
        // custom logic before showing login page...
        return "login";
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        model.addAttribute("auth", auth);
        model.addAttribute("sessionId", userSessionService.getUserSessionId());
        return "index";
    }

    @GetMapping("/signup")
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

    @PostMapping("/transaction")
    public  String processConfirm(@ModelAttribute Transaction transaction, @RequestParam String purchaseQuantity, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth", auth);

        transaction.setTransactionDate(Calendar.getInstance().getTime());
        transaction.setCreateUser("System");
        transaction.setCreateTs(Calendar.getInstance().getTime());

        //Setting EventId and AlumniId as null for testing, as no entry exists in respective tables.
        transaction.setEventId(transaction.getEventId());

        transaction.setAlumniId(transaction.getAlumniId());

        System.out.println("Transaction - " + transaction);

        Transaction txn = transactionRepository.save(transaction);

        Event event = eventRepository.getById(transaction.getEventId());

        System.out.println("Payment Successfully Saved - " + txn);
        System.out.println("Ticket Quantity - " + purchaseQuantity);

        String eventDateStr = getDateString(event.getDate());

        model.addAttribute("transaction", txn);
        model.addAttribute("event", event);
        model.addAttribute("eventDate", eventDateStr);
        model.addAttribute("ticketQuantity", purchaseQuantity);

        return "transaction_success";
    }

    private String getDateString(Date date) {

        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm aaa").format(date);
    }

    @GetMapping("/home")
    public String homeView() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //((WebAuthenticationDetails)((UsernamePasswordAuthenticationToken)auth).details).sessionId
        return "home";
    }

    @GetMapping("/contactus")
    public String contactusView(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth", auth);
        return "contact_us";
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
