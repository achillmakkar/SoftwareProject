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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


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


    //Codeanfang_Lang_30.04.2024/01.05.2024_Histogram_Data

    public List<OccupancyHistogramData> calculateHistogramData() {
        List<OccupancyHistogramData> result = new ArrayList<>();

        // Get all occupancies
        List<Occupancy> allOccupancies = (List<Occupancy>) occupancyRepository.findAll();

        // Determine the oldest and youngest years
        Integer oldestYear = Occupancy.findOldestYear(allOccupancies);
        Integer youngestYear = Occupancy.findYoungestYear(allOccupancies);

        // Ensure valid data is available
        if (oldestYear == null || youngestYear == null) {
            return result;
        }

        // Determine the current year and month
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        // Calculate the start and end year-month for each 12-month period
        for (int year = oldestYear - 1; year <= youngestYear; year++) {
            int startYear = year;
            int startMonth = currentMonth;
            int endYear = year + 1;
            int endMonth = currentMonth - 1;
            if (endMonth == 0) {
                endMonth = 12;
                endYear -= 1;
            }

            // SQL query to sum used rooms and beds for the 12-month period
            String sql = "SELECT " +
                    "    SUM(usedrooms) AS totalUsedRooms, " +
                    "    SUM(usedbeds) AS totalUsedBeds " +
                    "FROM occupancy " +
                    "WHERE (year > ? OR (year = ? AND month >= ?)) " +
                    "AND (year < ? OR (year = ? AND month <= ?))";

            // Execute the query and process the result
            Map<String, Object> queryResult = jdbcTemplate.queryForMap(sql,
                    startYear, startYear, startMonth,
                    endYear, endYear, endMonth);

            // Check for nulls before converting to int
            Number totalUsedRoomsNum = (Number) queryResult.get("totalUsedRooms");
            Number totalUsedBedsNum = (Number) queryResult.get("totalUsedBeds");

            // Initialize to 0 if null
            int totalUsedRooms = totalUsedRoomsNum != null ? totalUsedRoomsNum.intValue() : 0;
            int totalUsedBeds = totalUsedBedsNum != null ? totalUsedBedsNum.intValue() : 0;

             if (totalUsedRooms > 0 && totalUsedBeds > 0) {
                String period = String.format("%02d.%d - %02d.%d",
                        startMonth, startYear,
                        endMonth, endYear);
                result.add(new OccupancyHistogramData(period, totalUsedRooms, totalUsedBeds));
             }
        }

        return result;
    }

}

//VEREINFACHTER CODE mit vollen Jahren:
    /*
    public List<OccupancyHistogramData> calculateHistogramData() {
        List<OccupancyHistogramData> result = new ArrayList<>();

        // Get all occupancies
        List<Occupancy> allOccupancies = (List<Occupancy>) occupancyRepository.findAll();

        // Determine the oldest and youngest years
        Integer oldestYear = Occupancy.findOldestYear(allOccupancies);
        Integer youngestYear = Occupancy.findYoungestYear(allOccupancies);

        // Loop through each year from the oldest to the most recent year
        for (int year = oldestYear; year <= youngestYear; year++) {
            // SQL query to sum used rooms and beds for the whole year
            String sql = "SELECT " +
                    "    SUM(usedrooms) AS totalUsedRooms, " +
                    "    SUM(usedbeds) AS totalUsedBeds " +
                    "FROM occupancy " +
                    "WHERE year = ?";

            // Execute the query and process the result
            Map<String, Object> queryResult = jdbcTemplate.queryForMap(sql, year);

            // Check for nulls before converting to int
            Number totalUsedRoomsNum = (Number) queryResult.get("totalUsedRooms");
            Number totalUsedBedsNum = (Number) queryResult.get("totalUsedBeds");

            // Initialize to 0 if null
            int totalUsedRooms = totalUsedRoomsNum != null ? totalUsedRoomsNum.intValue() : 0;
            int totalUsedBeds = totalUsedBedsNum != null ? totalUsedBedsNum.intValue() : 0;

            if (totalUsedRooms > 0 && totalUsedBeds > 0) {
                String period = Integer.toString(year); // Create a period string like "2013"
                result.add(new OccupancyHistogramData(period, totalUsedRooms, totalUsedBeds));
            }
        }

        return result;
    }
*/

//Codeende_Lang_30.04.2024/01.05.2024_Histogram_Data

// Codeende_Achill_24.03.2024/16.04.2024_OccupancyService
