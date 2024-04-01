package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

  @Value("${welcome.message}")
  private String message;

  private HotelService hotelService;

  @Autowired
  public MainController(final HotelService hotelService) {
    this.hotelService = hotelService;
  }

  //Codestart_Achill_20.03.2024_PagePerPage
  @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
  public String index(final Model model,
                      @RequestParam("page") Optional<Integer> page,
                      @RequestParam("size") Optional<Integer> size) {
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(10);

    Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
    Page<Hotel> hotelPage = hotelService.findAllOrderedById(pageable);

    int totalPages = hotelPage.getTotalPages();
    int window = 2; // Anzahl der Seiten links und rechts von der aktuellen Seite
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

    model.addAttribute("hotelPage", hotelPage);
    model.addAttribute("currentPage", currentPage);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("pageNumbers", pageNumbers);

    return "index";
  }
  //Codeende_Achill_20.03.2024_PagePerPage

}
