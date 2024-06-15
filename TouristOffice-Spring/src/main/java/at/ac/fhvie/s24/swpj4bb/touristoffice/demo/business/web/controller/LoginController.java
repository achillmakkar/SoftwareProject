package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

  @GetMapping("/loginpage")
  public String login(Model model) {
    model.addAttribute("command",new User());
    return "loginpage";
  }

  @PostMapping("/loginpage")
  public String forwardLogin() {
    return "redirect:/index";}

}