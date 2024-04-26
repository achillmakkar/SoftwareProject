//Codebegin_Lang_15.04.2024_Sub_button

package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    private final HotelRepository hotelRepository;

    @Autowired
    public SubscriptionService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public boolean updateSubscription(String hotelId) {

        // Find the hotel by ID
        Hotel hotel = hotelRepository.findById(Integer.parseInt(hotelId)).orElse(null);
        if (hotel != null) {
            // Update the subscribed attribute
            hotel.setSubscribed(!hotel.isSubscribed());

            // Save the updated hotel back to the database
            hotelRepository.save(hotel);
            return true; // Erfolgreich aktualisiert
        } else {
            return false; // Hotel nicht gefunden
        }
    }
}
//Codeend_15.04.2024_Lang_Sub_button