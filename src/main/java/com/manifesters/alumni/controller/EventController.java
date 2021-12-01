package com.manifesters.alumni.controller;

import com.manifesters.alumni.service.EventService;
import com.manifesters.alumni.types.Event;
import com.manifesters.alumni.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping("/event")
    public String populateEvents(Model model){
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
        service.saveEvent(newEvent);
        return "redirect:/event";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(       Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"), true));
    }
}
