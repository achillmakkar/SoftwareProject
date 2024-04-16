package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.HotelService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.OccupancyService;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.HotelValidator;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.OccupancyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
}
// Codeende_Achill_24.03.2024_OccupancyController
