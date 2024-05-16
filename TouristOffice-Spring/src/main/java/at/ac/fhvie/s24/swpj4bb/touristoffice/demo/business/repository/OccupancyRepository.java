package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// Codeanfang_Achill_24.03.2024/16.04.2024_OccupancyRepository
public interface OccupancyRepository  extends PagingAndSortingRepository<Occupancy, Integer> {
    List<Occupancy> findAllByOrderByOccupancyidAsc();
    @Query(value = "SELECT o FROM Occupancy o ORDER BY o.occupancyid")
    Page<Occupancy> findAll(Pageable pageable);
    List<Occupancy> findAllByOccupancyid(int occupancyid, Pageable pageable);
//code sulim 18.04.

    List<Occupancy> findById(int occupancyId);
//code sulim 29.04 15:53 // angepasst am 04.05.2024 von hotelid zu hotel hotel


    List<Occupancy> findByHotelAndYear(Hotel hotel, int year); // Methode, um alle verfügbaren Jahre für ein bestimmtes Hotel abzurufen

    // code Sulim 16.05 -> 12 letzten einträge
    @Query("SELECT DISTINCT o FROM Occupancy o WHERE o.hotel = :hotel ORDER BY o.year DESC, o.month DESC")
    List<Occupancy> findTop12ByHotelOrderByYearDescMonthDesc(Hotel hotel, Pageable pageable);

    // Add this new method SULIM CODE isnt complete i need which years for a specific hotel - find all years in the occ table for the hotel id whatever.
    // get the Hotel ID das heisst mit LOOP nehmen wir mal die hotel ID
    // get -> all available years --> mit distinct years loopen wir und holen die jahre
    // In occupancydata --> occupancy data loopen für dieses jahr und für dieses hotel beziehen
    // for that hotel
    // -> retrieve occupancy data for that
    // hotel for that year
    //and the last 12 entries
    @Query("SELECT DISTINCT o.year FROM Occupancy o ORDER BY o.year")
    List<Integer> findDistinctYears();

}


// Codeende_Achill_24.03.2024/16.04.2024_OccupancyRepository
