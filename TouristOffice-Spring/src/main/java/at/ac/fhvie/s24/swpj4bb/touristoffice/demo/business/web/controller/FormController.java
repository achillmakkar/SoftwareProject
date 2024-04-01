package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;


import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.command.FooData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FormController {

  @GetMapping("/sampleform")
  public String sampleForm(final Model model) {
    model.addAttribute("command", new FooData());

    return "sampleform";
  }
// Code Anfang #3_Nikola_01.04_Add Occupancy Form - GetMapping für Occupancy Form Seite einfügen
  @GetMapping("/occupancyform")
  public String occupancyForm(final Model model) {
    model.addAttribute("command", new FooData());

    return "occupancyform";
  }
  // Code Ende #3_Nikola_01.04_Add Occupancy Form - GetMapping für Occupancy Form Seite einfügen

  @ModelAttribute("multiCheckboxAllValues")
  public String[] getMultiCheckboxAllValues() {
    return new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
        "Sunday"};
  }


  // set of values applied to a single-select radio button set, and drop-down list.
  @ModelAttribute("singleSelectAllValues")
  public String[] getSingleSelectAllValues() {
    return new String[] {"YES", "NO", "MAYBE"};
  }


  @PostMapping("/sampleform")
  public String foobarPost(
      @ModelAttribute("command") final FooData fooData,
      // WARN: BindingResult *must* immediately follow the Command.
      // https://stackoverflow.com/a/29883178/1626026
      final BindingResult bindingResult,
      final Model model,
      final RedirectAttributes ra) {

    log.debug("form submission.");

    if (bindingResult.hasErrors()) {
      return "sampleform";
    }

    ra.addFlashAttribute("command", fooData);

    return "redirect:/sampleresult";
  }

  @GetMapping("/sampleresult")
  public String fooresult(
      @ModelAttribute("command") final FooData command,
      final Model model) {

    log.debug("!!!" + command.toString());

    return "/";
  }

}