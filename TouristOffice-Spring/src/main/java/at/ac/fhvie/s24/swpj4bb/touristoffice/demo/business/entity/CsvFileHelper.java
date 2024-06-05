package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity;

import at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.validation.CsvValidator;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Year;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHelper {

    //  Codeanfang_Achill_ImportCsv_DataService/18.04.2024/19.04.2024/20.04.2024/23.04.2024/25.04.2024/26.04.2024
    public static List<Occupancy> convertCsvToListOfOccupancy(InputStream is) throws CsvValidator {
        List<Occupancy> occupancies = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';') // Semikolon als Trennzeichen verwenden
                .build();

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is))
                .withCSVParser(parser)
                .build()) {

            String[] line;
            int lineNumber = 0;
            int currentYear = Year.now().getValue();
            LocalDate currentDate = LocalDate.now();
            int currentMonth = currentDate.getMonthValue();
            while ((line = reader.readNext()) != null) {
                lineNumber++;
                if (lineNumber == 1) {
                    continue; // Überspringen der Kopfzeile
                }
                if (line.length < 7) {
                    throw new CsvValidator("Fehlende Attribute in Zeile: " + lineNumber);
                }

                Hotel hotel = new Hotel();
                hotel.setId(parseInteger(line[0].trim(), "Hotel ID", lineNumber));
                int rooms = parseInteger(line[1].trim(), "Rooms", lineNumber);
                int usedRooms = parseInteger(line[2].trim(), "Used Rooms", lineNumber);
                int beds = parseInteger(line[3].trim(), "Beds", lineNumber);
                int usedBeds = parseInteger(line[4].trim(), "Used Beds", lineNumber);
                int year = parseInteger(line[5].trim(), "Year", lineNumber);
                int month = parseInteger(line[6].trim(), "Month", lineNumber);

                if (rooms < 0 || usedRooms < 0 || beds < 0 || usedBeds < 0) {
                    throw new CsvValidator("Negative Werte in Zeile: " + lineNumber);
                }
                if (usedRooms > rooms) {
                    throw new CsvValidator("Used Rooms dürfen nicht größer als Rooms sein in Zeile: " + lineNumber);
                }
                if (usedBeds > beds) {
                    throw new CsvValidator("Used Beds dürfen nicht größer als Beds sein in Zeile: " + lineNumber);
                }
                if (year > currentYear || (year == currentYear && month > currentMonth)) {
                    throw new CsvValidator("Das Datum darf nicht in der Zukunft liegen in Zeile: " + lineNumber);
                }
                if (month < 1 || month > 12) {
                    throw new CsvValidator("Das Monat muss zwischen 1 und 12 liegen in Zeile: " + lineNumber);
                }

                // aktuelles datum...
                // fehlermeldung wenn ordner nicht existiert

                Occupancy occupancy = new Occupancy();
                occupancy.setHotel(hotel);
                occupancy.setRooms(rooms);
                occupancy.setUsedrooms(usedRooms);
                occupancy.setBeds(beds);
                occupancy.setUsedbeds(usedBeds);
                occupancy.setYear(year);
                occupancy.setMonth(month);
                occupancies.add(occupancy);
            }
        } catch (Exception e) {
            throw new CsvValidator("Fehler beim Lesen der CSV-Datei: " + e.getMessage());

        }
        return occupancies;
    }
    private static int parseInteger(String value, String fieldName, int lineNumber) throws CsvValidator {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new CsvValidator("Ungültiger Wert für " + fieldName + " in Zeile " + lineNumber + ": " + value);
        }
    }
    //  Codeende_Achill_ImportCsv_DataService/18.04.2024/19.04.2024/20.04.2024/23.04.2024/25.04.2024/26.04.2024
}
