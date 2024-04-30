package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.OccupancyService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.OccupancyValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.OccupancyHistogramData;


import java.util.List;

// Codeanfang_Achill_24.03.2024_OccupancyController
@Controller
public class OccupancyController
{
    private final OccupancyValidator occupancyValidator;
    private final OccupancyService occupancyService;

    public OccupancyController(final OccupancyValidator occupancyValidator, final OccupancyService occupancyService)
    {
        this.occupancyValidator = occupancyValidator;
        this.occupancyService = occupancyService;
    }

    // Codeende_Achill_24.03.2024_OccupancyController

    // code sulim 18.04.
    @GetMapping("/occupancy/by-hotel/{Id}")
    public ResponseEntity<List<Occupancy>> getOccupancyByHotelId(@PathVariable int id) {
        List<Occupancy> occupancies = occupancyService.findOccupanciesById(id);
        return ResponseEntity.ok(occupancies);
    }

    //Codebegin_Lang_30.04.2024_Histogram_Data
    @GetMapping("/occupancy/histogram-data")
    public ResponseEntity<List<OccupancyHistogramData>> getHistogramData() {
        List<OccupancyHistogramData> data = occupancyService.calculateHistogramData();
        return ResponseEntity.ok(data);
    }
    //Codeend_Lang_30.04.2024_Histogram_Data
}


