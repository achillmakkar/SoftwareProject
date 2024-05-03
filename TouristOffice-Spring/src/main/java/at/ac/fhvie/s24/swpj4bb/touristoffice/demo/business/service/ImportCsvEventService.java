package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.ImportCsvEvent;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.ImportCsvEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// Codeanfang_Achill_02.05.2024/03.05.2024_ImportCsvEvent
@Service
public class ImportCsvEventService {
    @Autowired
    private ImportCsvEventRepository importEventRepository;

    public void logImportEvent(String status, String user) {
        ImportCsvEvent event = new ImportCsvEvent();
        event.setTimestamp(LocalDateTime.now());
        event.setStatus(status);
        event.setUser(user);
        importEventRepository.save(event);
    }

    public void logSummaryEvent(int filesOk, int filesFailed, String user) {
        String status = filesOk + "-OK, " + filesFailed + "-FAILED";
        logImportEvent(status, user);
    }

}
// Codeende_Achill_02.05.2024/03.05.2024_ImportCsvEvent
