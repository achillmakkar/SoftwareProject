package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.HotelService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.OccupancyService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.HotelValidator;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.OccupancyValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Codeanfang_Achill_24.03.2024_OccupancyController
@Controller
public class OccupancyController
{
    private final OccupancyValidator occupancyValidator;
    private final OccupancyService occupancyService;

    public OccupancyController(final OccupancyValidator occupancyValidator, final OccupancyService occupancyService)
    {
        this.occupancyValidator = occupancyValidator;
        this.occupancyService = occupancyService;
    }

    //CodeAnfang Nikola 12.04.
  /*  @PostMapping("/submitOccupancy")
    public String submitOccupancy(@ModelAttribute("command") Occupancy command,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "occupancyform";
        }
        redirectAttributes.addFlashAttribute("command", command);
        return "redirect:/occupancyformfilledout";
    }



    @GetMapping("/occupancyformfilledout")
    public String occupancyFormFilledOut(@RequestParam("hotelID") int hotelID,
                                         @RequestParam("hotelName") String hotelName,
                                         @RequestParam("zip") String zip,
                                         @RequestParam("year") int year,
                                         @RequestParam("month") int month,
                                         @RequestParam("maxRooms") int maxRooms,
                                         @RequestParam("bookedRooms") int bookedRooms,
                                         @RequestParam("maxBeds") int maxBeds,
                                         @RequestParam("bookedBeds") int bookedBeds) {
        if (hotelID == 0 ||
                hotelName == null ||
                zip == null ||
                year == 0 ||
                month == 0 ||
                maxRooms == 0 ||
                maxBeds == 0 ) {
            return "redirect:/occupancyform";
        }
        return "occupancyformfilledout";
    }

    @PostMapping("/finalizesave")
    public String finalizeSave(@RequestParam("hotelID") int hotelID,
                               @RequestParam("hotelName") String hotelName,
                               @RequestParam("zip") String zip,
                               @RequestParam("year") int year,
                               @RequestParam("month") int month,
                               @RequestParam("maxRooms") int maxRooms,
                               @RequestParam("bookedRooms") int bookedRooms,
                               @RequestParam("maxBeds") int maxBeds,
                               @RequestParam("bookedBeds") int bookedBeds) {
    try
    {
        Occupancy occupancy = new Occupancy();

        occupancy.setId(hotelID);
        occupancy.setYear(year);
        occupancy.setMonth(month);
        occupancy.setRooms(maxRooms);
        occupancy.setUsedrooms(bookedRooms);
        occupancy.setBeds(maxBeds);
        occupancy.setUsedbeds(bookedBeds);


        occupancyService.saveOccupancy(occupancy);
        return "redirect:/successpage";
    } catch (Exception ex) {
        ex.printStackTrace();
        return "error";
    }
    }
*/
    //CodeEnde Nikola 12.04.
}
// Codeende_Achill_24.03.2024_OccupancyController

