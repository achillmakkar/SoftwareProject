package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.CsvFileHelper;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.OccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class DataService {

    @Autowired
    private OccupancyRepository occupancyRepository;


    private final Path correctFilesPath = Paths.get(System.getProperty("user.home"), "correct_files");
    private final Path problemFilesPath = Paths.get(System.getProperty("user.home"), "problem_files");

    @Autowired
    public DataService(final OccupancyRepository occupancyRepository) {
        this.occupancyRepository = occupancyRepository;
        createDirectoriesIfNeeded(correctFilesPath);
        createDirectoriesIfNeeded(problemFilesPath);
    }

    public void processFiles(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                processFile(file);
            }
        }
    }

    public boolean processFile(MultipartFile file) {
        try {
            List<Occupancy> occupancies = CsvFileHelper.convertCsvToListOfOccupancy(file.getInputStream());
            for (Occupancy occupancy : occupancies) {
                occupancyRepository.save(occupancy);
            }
            // Speichere die korrekte Datei im entsprechenden Ordner
            Files.copy(file.getInputStream(), correctFilesPath.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            // Speichere die problematische Datei im entsprechenden Ordner
            try {
                Files.copy(file.getInputStream(), problemFilesPath.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    private void createDirectoriesIfNeeded(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
