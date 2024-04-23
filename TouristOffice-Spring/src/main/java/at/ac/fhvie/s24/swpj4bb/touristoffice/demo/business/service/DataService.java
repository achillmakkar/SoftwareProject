package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.CsvFileHelper;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.OccupancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;


@Service

public class DataService {

    @Autowired
    private OccupancyRepository occupancyRepository;

    @Autowired
    private OccupancyService occupancyService;

    // Pfad aus der Konfigurationsdatei lesen
    @Value("${csv.directory.path}")
    private String csvDirectoryPath;

    private final Path correctFilesPath = Paths.get(System.getProperty("user.home"), "correct_files");
    private final Path problemFilesPath = Paths.get(System.getProperty("user.home"), "problem_files");

    @Autowired
    public DataService(final OccupancyRepository occupancyRepository) {
        this.occupancyRepository = occupancyRepository;
        createDirectoriesIfNeeded(correctFilesPath);
        createDirectoriesIfNeeded(problemFilesPath);
    }

    public boolean processFile(Path path) {
        if (Files.exists(path) && !Files.isDirectory(path)) {
            try (InputStream is = Files.newInputStream(path)) {
                List<Occupancy> occupancies = CsvFileHelper.convertCsvToListOfOccupancy(is);
                for (Occupancy occupancy : occupancies) {
                    occupancyService.saveOccupancy(occupancy);
                }
                // Verschieben der korrekten Datei in den entsprechenden Ordner
                Path destination = correctFilesPath.resolve(path.getFileName());
                Files.move(path, destination, StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (IOException e) {
                logProblemFile(path, e);
                return false;
            }
        }
        return false;
    }

    private void logProblemFile(Path path, IOException e) {
        e.printStackTrace();
        try {
            Files.copy(path, problemFilesPath.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
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

    public boolean importCsvFilesFromDirectory() {
        try {
            File directory = ResourceUtils.getFile(csvDirectoryPath);
            Path pathToCsv = directory.toPath();

            try (Stream<Path> paths = Files.walk(pathToCsv)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".csv"))
                        .forEach(this::processFile);

                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
