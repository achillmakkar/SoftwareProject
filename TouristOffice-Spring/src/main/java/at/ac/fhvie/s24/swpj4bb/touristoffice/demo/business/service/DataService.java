package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.ExcelHelper;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.OccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class DataService {

    @Autowired
    private OccupancyRepository occupancyRepository;

    public boolean processExcelFile(MultipartFile file) {
        try {
            List<Occupancy> occupancies = ExcelHelper.convertExcelToListOfOccupancy(file.getInputStream());
            for (Occupancy occupancy : occupancies) {
                occupancyRepository.save(occupancy);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
