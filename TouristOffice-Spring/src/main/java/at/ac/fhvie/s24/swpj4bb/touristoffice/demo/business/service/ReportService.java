package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.service;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Hotel;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.Occupancy;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity.OccupancyHistogramData;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.HotelRepository;
import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.repository.OccupancyRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.jfree.chart.axis.CategoryLabelPositions;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Codeanfang_Achill_02.05.2024/03.05.2024/04.05.2024_StatisticsAsPDF
@Service
public class ReportService {
    @Autowired
    private OccupancyRepository repository;
    @Autowired
    private OccupancyService occupancyService;

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    public boolean exportReport(String reportFormat) {
        log.info("Exporting report - format: {}", reportFormat);
        try {
            String basePath = System.getProperty("user.dir");
            String reportFolderPath = basePath + File.separator + "reports";
            File reportDir = new File(reportFolderPath);
            if (!reportDir.exists()) {
                boolean created = reportDir.mkdirs();
                log.info("Report directory created: {}", created);
            } else {
                log.info("Report directory already exists.");
            }

            List<Occupancy> occupancies = repository.findAllByOrderByOccupancyidAsc();
            if (occupancies.isEmpty()) {
                log.warn("No occupancy data found.");
            }

            ClassPathResource reportResource = new ClassPathResource("jasper/allhotels.jrxml");
            try (InputStream reportInputStream = reportResource.getInputStream()) {
                JasperReport jasperReport = JasperCompileManager.compileReport(reportInputStream);
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(occupancies);
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("createdBy", "Achill");

                List<OccupancyHistogramData> histogramData = occupancyService.calculateHistogramData();
                if (histogramData.isEmpty()) {
                    log.warn("No histogram data calculated.");
                }

                JFreeChart chart = createChart(histogramData);
                BufferedImage chartImage = getChartImage(chart);
                parameters.put("CHART_IMAGE", chartImage);

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                String outputFile = reportFolderPath + File.separator + "occupancy." + reportFormat.toLowerCase();
                if ("html".equalsIgnoreCase(reportFormat)) {
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, outputFile);
                } else if ("pdf".equalsIgnoreCase(reportFormat)) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
                }
                log.info("Report successfully exported: {}", outputFile);
            }

            return true;  // Report created
        } catch (IOException | JRException e) {
            log.error("Error generating report: ", e);
            return false;  // Error generating reports
        }
    }
    public byte[] generateHistogramReportAsPDF() throws JRException, IOException {
        List<OccupancyHistogramData> histogramData = occupancyService.calculateHistogramData();
        JFreeChart chart = createChart(histogramData);
        BufferedImage chartImage = getChartImage(chart);

        InputStream reportStream = getClass().getResourceAsStream("/jasper/allhotels.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CHART_IMAGE", chartImage);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JFreeChart createChart(List<OccupancyHistogramData> histogramData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (OccupancyHistogramData data : histogramData) {
            dataset.addValue(data.getTotalUsedRooms(), "Sum of all Hotel Room Occupancies", data.getPeriod());
            dataset.addValue(data.getTotalUsedBeds(), "Sum of all Hotel Bed Occupancies", data.getPeriod());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Occupancy Histogram", // Chart title
                "Period", // Domain axis label
                "Count", // Range axis label
                dataset, // Data
                PlotOrientation.VERTICAL,
                true, // Include legend
                true, // Tooltips
                false // URLs
        );


        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = plot.getDomainAxis();

        // Labels drehen, um mehr Platz zu schaffen
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4.0));

        // Schriftgröße anpassen
        xAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 189, 89));
        renderer.setSeriesPaint(1, new Color(74, 141, 208));

        return chart;
    }

    private BufferedImage getChartImage(JFreeChart chart) {
        return chart.createBufferedImage(800, 600);
    }
}
// Codeende_Achill_02.05.2024/03.05.2024/04.05.2024_StatisticsAsPDF
