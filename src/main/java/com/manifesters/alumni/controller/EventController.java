package com.manifesters.alumni.controller;

import com.manifesters.alumni.domain.TransactionType;
import com.manifesters.alumni.service.EventService;
import com.manifesters.alumni.types.Event;
import com.manifesters.alumni.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping("/events")
    public String populateEvents(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth", auth);
        List<Event> events  = service.getAllEvents(0, 15, "date");
        model.addAttribute("events", events );
        return "event";
    }

    @PostMapping(value="/create_event")
    public  String createEvent(@ModelAttribute Event event, @RequestParam MultipartFile file, Model model) throws IOException {
        // Save a file in upload directory
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileUploadUtil.saveFile( fileName, file);
        // Create a new event with imagePath
        Event newEvent = new Event(event.getEventName(), event.getVenue(), event.getDescription(), event.getDate(), fileName, event.getPrice());

        newEvent.setCreateUser("System");
        newEvent.setCreateTs(Calendar.getInstance().getTime());

        service.saveEvent(newEvent);
        return "redirect:/events";
    }

    @PostMapping(value = "delete_event")
    public String deleteEvent(@RequestParam Long eventId, Model model){
        service.deleteEvent(eventId);
        return "redirect:/events";
    }


    @PostMapping(value = "/buy_ticket")
    public String buyTicket(@RequestParam String id, @RequestParam String price, @RequestParam String quantity, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth", auth);

        model.addAttribute("eventId", id );
        model.addAttribute("eventType", TransactionType.Event.toString());
        model.addAttribute("eventPrice", price);
        model.addAttribute("purchaseQuantity", quantity);

        String priceStr = price.split("\\$")[1];

        double priceNew = Double.valueOf(priceStr);
        double subTotal = priceNew * Long.valueOf(quantity);
        double totalTax = subTotal * 0.02;
        double txnAmt = subTotal + totalTax;

        model.addAttribute("eventPrice", priceNew);
        model.addAttribute("subTotal", subTotal);
        model.addAttribute("tax", totalTax);
        model.addAttribute("totalAmount", txnAmt);

        return "payment_confirmation";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(       Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"), true));
    }
    
    
}
