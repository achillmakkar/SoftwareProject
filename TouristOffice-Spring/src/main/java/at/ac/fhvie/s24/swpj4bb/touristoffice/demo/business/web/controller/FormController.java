package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;


import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.OccupancyRepository;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.OccupancyService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.command.FooData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Controller
public class FormController {

  private final OccupancyService occupancyService;


  @GetMapping("/sampleform")
  public String sampleForm(final Model model) {
    model.addAttribute("command", new FooData());

    return "sampleform";
  }
// Code Anfang #3_Nikola_01.04_Add Occupancy Form - GetMapping für Occupancy Form Seite einfügen
  @GetMapping("/occupancyform")
  public String occupancyForm(Model model) {
    model.addAttribute("command", new Occupancy());
    return "occupancyform";
  }
  // Code Ende #3_Nikola_01.04_Add Occupancy Form - GetMapping für Occupancy Form Seite einfügen

  // Code Anfang #3_Nikola_16.04_Add Occupancy Form - PostMapping für Occupancy Form

  @Autowired
  public FormController(OccupancyService occupancyService) {
    this.occupancyService = occupancyService;
  }
  @PostMapping("/submitOccupancy")
  public RedirectView occupancyForm(HttpServletRequest request,
                                    RedirectAttributes redirectAttributes,
                                    @ModelAttribute Occupancy occupancy) {
    redirectAttributes.addFlashAttribute("command", occupancy);
    return new RedirectView("/occupancyformfilledout", true);
  }


  @GetMapping("/occupancyformfilledout")
  public String occupancyFormFilledOut(HttpServletRequest request) {
    Map<String,?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
    Occupancy occupancy = (Occupancy) inputFlashMap.get("command");
    return "occupancyformfilledout";
  }

  @PostMapping("/finalizesave")
  public String submitOccupancy(@ModelAttribute("command") Occupancy occupancy,
                                BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "occupancyform";
    }

    occupancyService.saveOccupancy(occupancy);

    return "redirect:/success";
  }
  // Code Ende #3_Nikola_16.04_Add Occupancy Form - Post/GetMapping für Occupancy Form
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