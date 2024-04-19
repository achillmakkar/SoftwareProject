package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHelper
{
    // TO DO
    // database table at what time imported
    // occupancy table seen when it was imported
    // files which worked and which not in ~/ -> Users Home
    // 3 to 4 files import automatic every month
    public static List<Occupancy> convertCsvToListOfOccupancy(InputStream is) {
        List<Occupancy> occupancies = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (reader.getLinesRead() == 1) continue; // Überspringen der Kopfzeile
                Occupancy occupancy = new Occupancy();
                occupancy.setOccupancyid(Integer.parseInt(line[0]));
                occupancy.setRooms(Integer.parseInt(line[1]));
                occupancy.setUsedrooms(Integer.parseInt(line[2]));
                occupancy.setBeds(Integer.parseInt(line[3]));
                occupancy.setUsedbeds(Integer.parseInt(line[4]));
                occupancy.setYear(Integer.parseInt(line[5]));
                occupancy.setMonth(Integer.parseInt(line[6]));
                occupancies.add(occupancy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return occupancies;
    }
}
