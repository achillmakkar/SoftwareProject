//Codebegin_Lang_15.04.2024_Sub_button

package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.SubscriptionService;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/updateSubscription")
    public String updateSubscription(@RequestBody SubscriptionRequest request) {

        String hotelId = request.getHotelId();

        System.out.println("Received request to update subscription for hotel with ID: " + hotelId);

        boolean subscribed = subscriptionService.updateSubscription(hotelId);

        return "{\"subscribed\": " + subscribed + "}";
    }

    @Setter
    @Getter
    public static class SubscriptionRequest {
        private String hotelId;
    }
}
//Codeend_15.04.2024_Lang_Sub_button