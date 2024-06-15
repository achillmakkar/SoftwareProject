package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.OccupancyService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.command.FooData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
  // Code Anfang #3_Nikola_17.04_Add Occupancy Form - Mapping für Occupancy Form
  @GetMapping("/occupancyform")
  public String occupancyForm(Model model) {              // Initialiserung des Formulars - "occupancy" Objekt wird erstellt und an Model gebunden
    model.addAttribute("occupancy", new Occupancy());   // Model stellt bildlich dar (Datentransfer zw Controller und View)
    return "occupancyform";                                    // Occupancy Objekt wird an 'model' gebunden - Userdaten werden gespeichert
  }

  @Autowired
  public FormController(OccupancyService occupancyService) { //saveOccupancy aus Occupancy Service wird aufgerufen
    this.occupancyService = occupancyService;
  }
  // Occupancy Service wird bereitgestellt und in FormController eingefügt
  @PostMapping("/submitOccupancy")
  public RedirectView occupancyForm(HttpServletRequest request,
                                    RedirectAttributes redirectAttributes,
                                    @ModelAttribute Occupancy occupancy) { //Userdaten werden in "occupancy" geladen
    redirectAttributes.addFlashAttribute("occupancy", occupancy); // occupancy Objekt wird in redirectAttributes gespeichert (als Flash -notwendig für Datenübertragen per Redirect) damit es ann Occ filledout weitergeleitet werden kann

    return new RedirectView("/occupancyformfilledout", true); //User bekommt die Occ. filledout Seite zu sehen
  }

  //Quelle: https://www.baeldung.com/spring-web-flash-attributes
  @GetMapping("/occupancyformfilledout")
  public String occupancyFormFilledOut(HttpServletRequest request) {
    Map<String,?> inputFlashMap = RequestContextUtils.getInputFlashMap(request); //Die Methode greift auf die, in der vorigen Methode, gespeicherten FlashAttributes zu
    Occupancy occupancy = (Occupancy) inputFlashMap.get("occupancy"); //Daten aus "occupancy" werden abgerufen und dem User angezeigt
    return "occupancyformfilledout";
  }

  //Quelle: https://www.baeldung.com/spring-web-flash-attributes

  @PostMapping("/finalizesave")
  public String submitOccupancy(@ModelAttribute("occupancy") Occupancy occupancy, //HTML Daten werden an "occupancy" gebunden
                                BindingResult bindingResult) {                    // Validierungsfehler werden erfasst
    if (bindingResult.hasErrors()) {
      return "occupancyform";
    }                             // wenn keine Fehler vorliegen werden die Userdaten in der Datenbank gespeichert
    occupancyService.saveOccupancy(occupancy);

    return "redirect:/index";
  }
  // Code Ende #3_Nikola_17.04_Add Occupancy Form - Mapping für Occupancy Form

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
          @ModelAttribute("command") final FooData fooData,
          final Model model) {

    log.debug("!!!" + fooData.toString());

    return "/";
  }
}