package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.HotelRepository;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.OccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.OccupancyHistogramData;


import java.util.List;

// Codeanfang_Achill_24.03.2024_OccupancyService
@Service
public class OccupancyService
{
    // Codeanfang_Achill_24.03.2024/16.04.2024_OccupancyService
    private final OccupancyRepository occupancyRepository;
    //Codeanfang_Lang_30.04.2024_Histogram_Data
    private final JdbcTemplate jdbcTemplate;
    //Codeende_Lang_30.04.2024_Histogram_Data

    @Autowired
    public OccupancyService(final OccupancyRepository occupancyRepository, JdbcTemplate jdbcTemplate)
    {
        this.occupancyRepository = occupancyRepository;
        //Codeanfang_Lang_30.04.2024_Histogram_Data
        this.jdbcTemplate = jdbcTemplate;
        //Codeende_Lang_30.04.2024_Histogram_Data
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
        occupancyRepository.save(occupancy);  //nimmt Objekt aus FormController engegen
    }
    // Codeende_Nikola_07.04.2024_SaveOccupancy

    //Codeanfang_Lang_30.04.2024_Histogram_Data
    public List<OccupancyHistogramData> calculateHistogramData() {
        String sql = "SELECT " +
                "FORMATDATETIME(DATEADD('MONTH', -(12 * num + 1), CURRENT_DATE()), 'yyyy-MM') AS startPeriod, " +
                "FORMATDATETIME(DATEADD('MONTH', -(12 * (num - 1)), CURRENT_DATE()), 'yyyy-MM') AS endPeriod, " +
                "SUM(usedrooms) AS totalRooms " +
                "FROM occupancy " +
                "JOIN (SELECT 0 AS num UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) AS years " +
                "ON year * 12 + month >= YEAR(DATEADD('MONTH', -(12 * num + 1), CURRENT_DATE())) * 12 + MONTH(DATEADD('MONTH', -(12 * num + 1), CURRENT_DATE())) " +
                "AND year * 12 + month < YEAR(DATEADD('MONTH', -(12 * (num - 1)), CURRENT_DATE())) * 12 + MONTH(DATEADD('MONTH', -(12 * (num - 1)), CURRENT_DATE())) " +
                "GROUP BY startPeriod, endPeriod " +
                "ORDER BY startPeriod DESC";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new OccupancyHistogramData(
                        rs.getString("startPeriod") + " to " + rs.getString("endPeriod"),
                        rs.getInt("totalRooms")
                )
        );
    }
    //Codeende_Lang_30.04.2024_Histogram_Data

}
// Codeende_Achill_24.03.2024/16.04.2024_OccupancyService
