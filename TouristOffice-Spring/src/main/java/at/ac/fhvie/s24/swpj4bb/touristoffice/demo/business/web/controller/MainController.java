package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.HotelService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.OccupancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

  @Value("${welcome.message}")
  private String message;

  private HotelService hotelService;
  //Codeanfang_Achill_16.04.2024_OccupancyData
  private OccupancyService occupancyService;

  private List<Occupancy> occupancies;

  @Autowired
  public MainController(final HotelService hotelService,  final OccupancyService occupancyService)
  {
    this.occupancyService = occupancyService;
    this.hotelService = hotelService;
  }


  //Codestart_Achill_20.03.2024_PagePerPage
  // Code angepasst von SUlim am 04.05.
  @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
  public String index(final Model model,
                      @RequestParam("page") Optional<Integer> page,
                      @RequestParam("size") Optional<Integer> size) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(10);

    Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
    Page<Hotel> hotelPage = hotelService.getHotelsOrderedByZipAndName(pageable);
    Page<Occupancy> occupancyPage = occupancyService.findAllOrderedById(pageable);

    if (occupancyPage.hasContent()) {
      model.addAttribute("occupancyPage", occupancyPage);
    } else {
      model.addAttribute("occupancyPage", Page.empty());
    }

    model.addAttribute("hotelPage", hotelPage);

    // Erweiterung: Abruf der Belegungsdaten nach Hotel und Jahr
    List<Integer> years = occupancyService.findDistinctYears();
    Map<Hotel, Map<Integer, List<Occupancy>>> hotelOccupancyMap = new HashMap<>();
    for (Hotel hotel : hotelPage.getContent()) {
      Map<Integer, List<Occupancy>> yearlyData = new HashMap<>();
      for (Integer year : years) {
        List<Occupancy> occupancy = occupancyService.getOccupancyDataForHotelAndYear(hotel, year);
        yearlyData.put(year, occupancy);
      }
      hotelOccupancyMap.put(hotel, yearlyData);
    }
    model.addAttribute("years", years);
    model.addAttribute("hotelOccupancyMap", hotelOccupancyMap);
    preparePaginationModel(model, currentPage, hotelPage.getTotalPages());

    return "index"; // Ensure that the 'index' view can display years and occupancy data
  }




  //Codeende_Achill_20.03.2024_PagePerPage

  //Codeanfang_Achill_16.04.2024_OccupancyData
  private void preparePaginationModel(Model model, int currentPage, int totalPages) {
    int window = 2;
    int start = Math.max(1, currentPage - window);
    int end = Math.min(totalPages, currentPage + window);

    if (start > 1) {
      model.addAttribute("startEllipsis", true);
    }
    if (end < totalPages) {
      model.addAttribute("endEllipsis", true);
    }

    List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
            .boxed()
            .collect(Collectors.toList());

    model.addAttribute("currentPage", currentPage);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("pageNumbers", pageNumbers);
  }
  //Codeende_Achill_16.04.2024_OccupancyData

}

