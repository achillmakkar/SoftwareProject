package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.CsvFileHelper;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.OccupancyRepository;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.CsvValidator;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


@Service

public class DataService {

    //  Codeanfang_Achill_ImportCsv_DataService/18.04.2024/19.04.2024/20.04.2024/23.04.2024/25.04.2024/26.04.2024
    @Autowired
    private OccupancyRepository occupancyRepository;

    @Autowired
    private OccupancyService occupancyService;

    @Autowired
    private ImportCsvEventService importCsvEventService;


    // Pfad aus der Konfigurationsdatei lesen

    private final Path csvDirectoryPath = Paths.get(System.getProperty("user.home"), "csvimport");
    private final Path correctFilesPath = csvDirectoryPath.resolve("correct_files");
    private final Path problemFilesPath = csvDirectoryPath.resolve("problem_files");



    @Autowired
    public DataService(final OccupancyRepository occupancyRepository) {
        this.occupancyRepository = occupancyRepository;
        createDirectoriesIfNeeded(csvDirectoryPath);
        createDirectoriesIfNeeded(correctFilesPath);
        createDirectoriesIfNeeded(problemFilesPath);
    }

    public boolean processFile(Path path) {
        if (Files.exists(path) && !Files.isDirectory(path)) {
            try (InputStream is = Files.newInputStream(path)) {
                try {
                    List<Occupancy> occupancies = CsvFileHelper.convertCsvToListOfOccupancy(is);
                    occupancies.forEach(occupancyService::saveOccupancy);
                    // Verschieben der korrekten Datei in den entsprechenden Ordner
                    moveFile(path, correctFilesPath);
                    return true;
                } catch (CsvValidator e) {
                    // Behandlung spezifischer CSV-Fehler
                    System.err.println("Fehler bei der Verarbeitung der Datei: " + path + ": " + e.getMessage());
                    moveFile(path, problemFilesPath);
                    return false;
                }
            } catch (IOException e) {
                System.err.println("IO-Fehler beim Lesen der Datei: " + path);
                return false;
            }
        }
        return false;
    }
    private void moveFile(Path source, Path targetDir) throws IOException {
        Files.move(source, targetDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
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

    public boolean importCsvFilesFromDirectory(String user) {
        if (!Files.exists(csvDirectoryPath)) {
            System.err.println("Das Verzeichnis 'csvimport' existiert nicht.");
            return false;
        }

        AtomicInteger okCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        try {
            File directory = ResourceUtils.getFile(csvDirectoryPath.toUri().toString());
            Path pathToCsv = directory.toPath();

            // Codeanfang_Achill_02.05.2024/03.05.2024_ImportCsvEvent
            try (Stream<Path> paths = Files.walk(pathToCsv)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".csv"))
                        .forEach(path -> {
                            if (processFile(path)) {
                                okCount.incrementAndGet();
                            } else {
                                failCount.incrementAndGet();
                            }
                        });

                // Log the summary after all files have been processed
                if (okCount.get() > 0 || failCount.get() > 0) {
                    importCsvEventService.logSummaryEvent(okCount.get(), failCount.get(), user);
                }
                return true;
                // Codeende_Achill_02.05.2024/03.05.2024_ImportCsvEvent
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

//  Codeende_Achill_ImportCsv_DataService/18.04.2024/19.04.2024/20.04.2024/23.04.2024/25.04.2024/26.04.2024

}
