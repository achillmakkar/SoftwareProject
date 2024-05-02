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

    /*
    //FEHLER IM CODE:
    public List<OccupancyHistogramData> calculateHistogramData() {
        List<OccupancyHistogramData> result = new ArrayList<>();

        // Get all occupancies
        List<Occupancy> allOccupancies = (List<Occupancy>) occupancyRepository.findAll();

        // Determine the oldest and youngest years
        Integer oldestYear = Occupancy.findOldestYear(allOccupancies);
        YearMonth now = YearMonth.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        // Iterate through periods
        for (int year = oldestYear - 1; year <= currentYear; year++) {
            // Determine the start and end year-month for the current period
            YearMonth start = YearMonth.of(year, 1);
            YearMonth end = year == currentYear ? now.minusMonths(1) : YearMonth.of(year + 1, 1).minusMonths(1);

            // SQL query to sum used rooms and beds in the period
            String sql = "SELECT " +
                    "    SUM(usedrooms) AS totalUsedRooms, " +
                    "    SUM(usedbeds) AS totalUsedBeds " +
                    "FROM occupancy " +
                    "WHERE (year * 12 + month) BETWEEN ? AND ?";

            // Calculate the month values for SQL parameters
            int startValue = start.getYear() * 12 + start.getMonthValue();
            int endValue = end.getYear() * 12 + end.getMonthValue();

            // Execute the query and process the result
            Map<String, Object> queryResult = jdbcTemplate.queryForMap(sql, startValue, endValue);

            // Retrieve the sums
            int totalUsedRooms = ((Number) queryResult.get("totalUsedRooms")).intValue();
            int totalUsedBeds = ((Number) queryResult.get("totalUsedBeds")).intValue();

            // Create a period string like "1.2013 to 12.2013"
            String period = start.getMonthValue() + "." + start.getYear() + " to " + end.getMonthValue() + "." + end.getYear();

            // Add the data to the result list
            result.add(new OccupancyHistogramData(period, totalUsedRooms, totalUsedBeds));
        }

        return result;
    }
    */

    //VEREINFACHTER CODE:
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

            // Create a period string like "2013"
            String period = Integer.toString(year);

            // Add the data to the result list
            result.add(new OccupancyHistogramData(period, totalUsedRooms, totalUsedBeds));
        }

        return result;
    }


    //TEST CODE GENERATOR:
    /*
    public List<OccupancyHistogramData> calculateHistogramData() {
        List<OccupancyHistogramData> data = new ArrayList<>();

        // Dummy data for years 2014-2019
        for (int year = 2014; year <= 2019; year++) {
            String period = Integer.toString(year);
            int totalUsedRooms = (int) (Math.random() * 1000); // Dummy random data
            int totalUsedBeds = (int) (Math.random() * 1000);  // Dummy random data

            data.add(new OccupancyHistogramData(period, totalUsedRooms, totalUsedBeds));
        }

        return data;
    }
    */

}





    //Codeende_Lang_30.04.2024/01.05.2024_Histogram_Data


// Codeende_Achill_24.03.2024/16.04.2024_OccupancyService
