package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Klasse zur Darstellung der aggregierten Daten für das Histogramm der Zimmerbelegungen.
 */
@Getter
@Setter
public class OccupancyHistogramData {
    private String period;  // Zeitraum im Format "YYYY-MM to YYYY-MM"
    private int totalUsedRooms;  // Gesamtzahl der genutzten Zimmer in diesem Zeitraum

    public OccupancyHistogramData(String period, int totalUsedRooms) {
        this.period = period;
        this.totalUsedRooms = totalUsedRooms;
    }
}
