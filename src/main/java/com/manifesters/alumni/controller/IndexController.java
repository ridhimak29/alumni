package com.manifesters.alumni.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    model.addAttribute("auth", auth);
return "index";
}
@RequestMapping(value = "/FAQ", method = RequestMethod.GET)
public String FAQPage(Model model) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    model.addAttribute("auth", auth);
return "FAQ";
}

}

