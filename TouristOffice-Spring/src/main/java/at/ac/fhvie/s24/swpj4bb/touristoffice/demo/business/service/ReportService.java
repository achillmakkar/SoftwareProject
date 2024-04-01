package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.HotelRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

// Codeanfang_Achill_01.04.2024_StatisticsAsPDF
@Service
public class ReportService
{
  @Autowired
  private HotelRepository repository;

    public boolean exportReport(String reportFormat) {
        try {
            String basePath = System.getProperty("user.dir");
            String reportFolderPath = basePath + File.separator + "reports";
            File reportDir = new File(reportFolderPath);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            List<Hotel> hotels = repository.findAllByOrderByIdAsc();
            ClassPathResource reportResource = new ClassPathResource("jasper/allhotels.jrxml");
            InputStream reportInputStream = reportResource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportInputStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(hotels);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Achill");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            if ("html".equalsIgnoreCase(reportFormat)) {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, reportFolderPath + File.separator + "hotels.html");
            } else if ("pdf".equalsIgnoreCase(reportFormat)) {
                JasperExportManager.exportReportToPdfFile(jasperPrint, reportFolderPath + File.separator + "hotels.pdf");
            }

            reportInputStream.close();

            return true; // Report created
        } catch (IOException | JRException e) {
            e.printStackTrace();
            return false; // Error generating reports
        }
    }
}
// Codeende_Achill_01.04.2024_StatisticsAsPDF -->
