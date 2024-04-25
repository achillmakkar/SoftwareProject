package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.HotelRepository;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.OccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// Codeanfang_Achill_24.03.2024_OccupancyService
@Service
public class OccupancyService
{
    // Codeanfang_Achill_24.03.2024/16.04.2024_OccupancyService
    private final OccupancyRepository occupancyRepository;
    @Autowired
    public OccupancyService(final OccupancyRepository occupancyRepository)
    {
        this.occupancyRepository = occupancyRepository;
    }

    public List<Occupancy> findAllOrderedById() {
        // Return the occupancy currently loaded
        return occupancyRepository.findAllByOrderByOccupancyidAsc();
    }
    public Page<Occupancy> findAllOrderedById(final Pageable pageRequest) {
        return occupancyRepository.findAll(pageRequest);
    }

    //Code Sulim 18.04.

    public List<Occupancy> findOccupanciesById(int Id) {
        return occupancyRepository.findById(Id);
    }



    //Code Sulim 19.04. for Occupancy years

    public List<Integer> findDistinctYears() {
        return occupancyRepository.findDistinctYears();
    }
    // Codeanfang_Nikola_07.04.2024_SaveOccupancy
    // Occupancy-Objekt wird in der Datenbank gespeichert
    public void saveOccupancy(Occupancy occupancy) {
        occupancyRepository.save(occupancy);
    }
    // Codeende_Nikola_07.04.2024_SaveOccupancy
}
// Codeende_Achill_24.03.2024/16.04.2024_OccupancyService
