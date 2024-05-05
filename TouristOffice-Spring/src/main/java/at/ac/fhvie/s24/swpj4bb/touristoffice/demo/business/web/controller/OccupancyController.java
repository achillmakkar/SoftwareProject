package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.OccupancyService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.ReportService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.OccupancyValidator;
import org.springframework.http.*;
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
    private final ReportService reportService;



    public OccupancyController(final OccupancyValidator occupancyValidator, final OccupancyService occupancyService, final ReportService reportService)
    {
        this.occupancyValidator = occupancyValidator;
        this.occupancyService = occupancyService;
        this.reportService = reportService;
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

    //Codeanfang_Achill_03.05.2024/04.05.2024_StatisticsAsPDF
    @GetMapping("/occupancy/histogram-report")
    public ResponseEntity<byte[]> getHistogramReport() {
        try {
            byte[] data = reportService.generateHistogramReportAsPDF();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("occupancy_histogram.pdf")
                    .build());
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Codeende_Achill_03.05.2024/04.05.2024_StatisticsAsPDF

}


