package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHelper {
    // TO DO
    // database table at what time imported
    // occupancy table seen when it was imported
    // files which worked and which not in ~/ -> Users Home
    // 3 to 4 files import automatic every month

    //  Codeanfang_Achill_ImportCsv_DataService/18.04.2024/19.04.2024/20.04.2024/23.04.2024/25.04.2024/26.04.2024
    public static List<Occupancy> convertCsvToListOfOccupancy(InputStream is) {
        List<Occupancy> occupancies = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';') // Semikolon als Trennzeichen verwenden
                .build();

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is))
                .withCSVParser(parser)
                .build()) {

            String[] line;
            while ((line = reader.readNext()) != null) {
                if (reader.getLinesRead() == 1) {
                    continue; // Überspringen der Kopfzeile
                }
                Occupancy occupancy = new Occupancy();
                Hotel hotel = new Hotel();
                hotel.setId(Integer.parseInt(line[0].trim()));
                occupancy.setHotel(hotel);
                occupancy.setRooms(Integer.parseInt(line[1].trim()));
                occupancy.setUsedrooms(Integer.parseInt(line[2].trim()));
                occupancy.setBeds(Integer.parseInt(line[3].trim()));
                occupancy.setUsedbeds(Integer.parseInt(line[4].trim()));
                occupancy.setYear(Integer.parseInt(line[5].trim()));
                occupancy.setMonth(Integer.parseInt(line[6].trim()));

                occupancies.add(occupancy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return occupancies;
    }
    //  Codeende_Achill_ImportCsv_DataService/18.04.2024/19.04.2024/20.04.2024/23.04.2024/25.04.2024/26.04.2024
}
