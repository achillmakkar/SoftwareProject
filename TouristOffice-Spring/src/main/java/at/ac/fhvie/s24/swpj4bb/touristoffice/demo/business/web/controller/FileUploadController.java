package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.web.controller;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController

public class FileUploadController
{
    @Autowired
    private DataService dataService;

    @GetMapping("/trigger-csv-import")
    public ResponseEntity<String> triggerCsvImport() {
        boolean success = dataService.importCsvFilesFromDirectory();

        if (success) {
            return ResponseEntity.ok("Der Import der CSV-Dateien wurde erfolgreich durchgeführt.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Beim Import der CSV-Dateien ist ein Fehler aufgetreten.");
        }
    }
}
