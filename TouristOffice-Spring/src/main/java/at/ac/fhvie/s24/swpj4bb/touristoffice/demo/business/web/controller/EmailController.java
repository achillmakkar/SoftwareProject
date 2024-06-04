package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendSubscriptionMail")
    public String sendSubscriptionMail() {
        emailService.sendMessageWithAttachment("hotelstatistics01@gmail.com", "Hotel Subscription",
                "Here is your requested PDF file.", "reports/occupancy.pdf");
        return "redirect:/index";
    }

    @GetMapping("/sendDATFile")
    public String sendDATFile() {
        // TODO: Change last parameter to DAT file path
        emailService.sendMessageWithAttachment("hotelstatistics01@gmail.com", "DAT File",
                "Here is your requested DAT file.", "CHANGE ME");
        return "redirect:/index";
    }
}