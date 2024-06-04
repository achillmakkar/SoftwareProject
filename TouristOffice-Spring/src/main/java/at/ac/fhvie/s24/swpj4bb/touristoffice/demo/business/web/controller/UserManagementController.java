package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserManagementController {

  @GetMapping("/index")
  public String index(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    model.addAttribute("username", userDetails.getUsername());
    model.addAttribute("roles", userDetails.getAuthorities());
    return "user/index";
  }
}