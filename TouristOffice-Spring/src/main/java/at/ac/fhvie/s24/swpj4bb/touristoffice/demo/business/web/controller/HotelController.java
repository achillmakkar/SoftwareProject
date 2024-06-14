package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.constants.Category;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.HotelService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.ReportService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.SubscriptionService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.HotelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HotelController {

  private final HotelValidator hotelValidator;
  private final HotelService hotelService;
  //Codebegin_Lang_15.04.2024_Sub_button--------------------------------------------------------------------------
  private final SubscriptionService subscriptionService;
  //Codeend_15.04.2024_Lang_Sub_button----------------------------------------------------------------------------

  @Autowired
  private ReportService reportService;

  public HotelController(final HotelValidator hotelvalidator, final HotelService hotelService, final SubscriptionService subscriptionService) {
    this.hotelValidator = hotelvalidator;
    this.hotelService = hotelService;
    //Codebegin_15.04.2024_Lang_Sub_button------------------------------------------------------------------------
    this.subscriptionService = subscriptionService;
    //Codeend_15.04.2024_Lang_Sub_button--------------------------------------------------------------------------
  }

  //Codebegin_Lang_15.04.2024/25.04.2024_Sub_button----------------------------------------------------------------------------

  @PostMapping("/{hotelId}/updateSubscription")
  @ResponseBody
  public ResponseEntity<Boolean> updateSubscription(@PathVariable String hotelId, @RequestBody(required = false) String body) {
    boolean subscribed = subscriptionService.updateSubscription(hotelId);

    //TO DO: besseren Code schreiben!!
    if (subscribed) {
      return ResponseEntity.ok(true);
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(false);
    }
  }
  //Codeend_15.04.2024/25.04.2024_Lang_Sub_button-------------------------------------------------------------------------------

  //Codebegin_23.05.2024_08.06.2024_LANG_add_hotel

  // Füge diese Methode hinzu, um die Hotelinformationen basierend auf der Hotel-ID abzurufen
  @GetMapping("/hotelinfo/{id}")
  @ResponseBody
  public ResponseEntity<Hotel> getHotelInfo(@PathVariable int id) {
    Hotel hotel = hotelService.findById(id);
    if (hotel != null) {
      return ResponseEntity.ok(hotel);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }
  @GetMapping("/deletehotel")
  public String deleteHotel(Model model) {              // Initialiserung des Formulars - "occupancy" Objekt wird erstellt und an Model gebunden
    model.addAttribute("hotel", new Hotel());   // Model stellt bildlich dar (Datentransfer zw Controller und View)
    return "deletehotel";                                    // Occupancy Objekt wird an 'model' gebunden - Userdaten werden gespeichert
  }
  @GetMapping("/addhotel")
  public String addHotelForm(Model model) {
    model.addAttribute("hotel", new Hotel());
    return "addhotel";
  }

  @PostMapping("/addhotel")
  public ResponseEntity<Object> addHotel(@RequestBody Hotel hotel) {
    hotelService.addHotel(hotel);
    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("hotelId", hotel.getId()); // Add this line to include the hotel ID in the response
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  //Codeend_23.05.2024_08.06.2024_LANG_add_hotel

  @GetMapping("/updatehotel/{id}")
  public String showUpdateHotelForm(@PathVariable("id") int id, Model model) {
    Hotel hotel = hotelService.findById(id);
    model.addAttribute("hotel", hotel);
    return "updatehotel";
  }

  // Here the form is called and the template is provided with an empty Hotel Instance
  @GetMapping("/hotelform")
  public String fooForm(final Model model) {
    model.addAttribute("command", new Hotel());

    return "hotelform";
  }


  // After submitting the Hotel form here is the entry point for the form POST where the form is
  // validated.
  // If there is an error the hotel entry form is presented once again with additional
  // error infomation If there is no error the page is redirected to an output page
  @PostMapping("/hotelform")
  public String hotelPost(
      @ModelAttribute("command") final Hotel command,
      // WARN: BindingResult *must* immediately follow the Command.
      // https://stackoverflow.com/a/29883178/1626026
      final BindingResult bindingResult,
      final Model model,
      final RedirectAttributes ra) {

    // bindingResult is for a certain kind of data validation and not used in this example.
    // The RedirectAttributes are similar to Attributes but witht he difference that the values are
    // maintained in the *Session* and are removed after the redirect.
    // With using Attribute the request parameters are created out of the attributes and are
    // serialized
    // RedriectAttributes are not serialized and can therefore store any object

    String error = hotelValidator.validate(command);
    // You have to format the error string for displaying it in HTML.
    // On a web page the \n is not valid and so no line feed is made.
    if (!error.isEmpty()) {
      // If there is an error the hotelform template gets the current values and the error string
      // Alternatively the error could be transmitted as a formatted HTML string or an ArrayList
      // with all the errors and then put together in the template
      // The ArrayList has the advantage that the creation of the error string is independent of
      // the frontend
      // (Swing/Web/App) but it has to be formatted later. A possibility could be in a Factory
      // Pattern where you provide the data and request a certain format and receive a
      // formatted String. So the Factory is responsible for the correct formatting. And further
      // formats are only implemented in that Factory.
      model.addAttribute("error", error);
      return "hotelform";
    }


    ra.addFlashAttribute("command", command);

    // Redirect to the output page. The hotel data is stored in an FlashAttribute which is
    // maintained in the session
    return "redirect:/hotelresult";

  }


  // Output of the hotel data entered by the user
  @GetMapping("/hotelresult")
  public String hotelOutput(
      @ModelAttribute("command") final Hotel hotel,
      final Model model) {

    System.out.println(hotel);

    // Category is not set in the form
    hotel.setCategory(Category.FOUR);

    hotelService.save(hotel);

    return "hotelresult";
  }


  // Codeanfang_Achill_01.04.2024_fuerTestzweckeangelegt(StatisticsAsPDF)
  @GetMapping("/hotels")
  public String listAllHotels(Model model) {
    List<Hotel> hotels = hotelService.findAllOrderedById();
    model.addAttribute("hotels", hotels);
    return "testhotels"; // HTML Datei testhotels.html
  }
  // Codeende_Achill_01.04.2024_fuerTestzweckeangelegt(StatisticsAsPDF) -->

  // Codeanfang_Achill_01.04.2024-StatisticsAsPDF -->
  @GetMapping("/report/{format}")
  public ResponseEntity<String> generateReport(@PathVariable String format) {
    if (reportService.exportReport(format)) {
      return ResponseEntity.ok("Report successfully generated!");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("There was an error generating the report.");
    }
  }
  // Codeende_Achill_01.04.2024-StatisticsAsPDF -->
  @DeleteMapping("/deleteHotel/{id}")
  public ResponseEntity<String> deleteHotel(@PathVariable int id) {
    try {
      hotelService.deleteHotelById(id);
      return ResponseEntity.ok("Hotel successfully deleted!");
    }
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("There was an error deleting the hotel.");
    }
  }
}