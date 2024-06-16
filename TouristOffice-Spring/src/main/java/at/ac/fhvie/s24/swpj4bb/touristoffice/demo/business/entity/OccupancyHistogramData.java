//Codeanfang_Lang_30.04.2024/01.05.2024_Histogram_Data

package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Klasse zur Darstellung der aggregierten Daten für das Histogramm der Zimmerbelegungen.
 */
@Getter
@Setter
public class OccupancyHistogramData {
    private String period;
    private int totalUsedRooms;
    private int totalUsedBeds;

    public OccupancyHistogramData(String period, int totalUsedRooms, int totalUsedBeds) {
        this.period = period;
        this.totalUsedRooms = totalUsedRooms;
        this.totalUsedBeds = totalUsedBeds;
    }
}

//Codeende_Lang_30.04.2024/01.05.2024_Histogram_Data