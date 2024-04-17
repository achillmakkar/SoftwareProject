package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @GetMapping("/loginpage")
  public String login() {
    return "loginpage";
  }
}
