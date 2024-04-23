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
    @Query(value = "SELECT o FROM Occupancy o ORDER BY occupancyid")
    Page<Occupancy> findAll(Pageable pageable);
    List<Occupancy> findAllByOccupancyid(int occupancyid, Pageable pageable);
//code sulim 18.04.

    List<Occupancy> findById(int occupancyId);


    // Add this new method SULIM CODE
    @Query("SELECT DISTINCT o.year FROM Occupancy o ORDER BY o.year")
    List<Integer> findDistinctYears();
}


// Codeende_Achill_24.03.2024/16.04.2024_OccupancyRepository
