package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// CodeAnfang Nikola Button for Monthly Subscription 25.05.2024

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendSubscriptionMail")
    public String sendSubscriptionMail() {
        return emailService.sendMessageWithAttachment("nikola.marunic@icloud.com", "Hotel Subscription",
                "Here is your requested PDF file.", "reports/hotels.pdf", "occupancy.pdf");
    }

    @GetMapping("/sendHotelBackupFilePerEmail")
    public String sendHotelBackupFilePerEmail() {
        String backupPath = System.getProperty("user.home") + "/backup/hotels_backup.zip";
        return emailService.sendMessageWithAttachment("nikola.marunic@icloud.com", "Hotel Backup",
                "Here is your requested hotel backup file.", backupPath, "hotel_backup.zip");
    }
// CodeEnde Nikola Button for Monthly Subscription 25.05.2024
}


