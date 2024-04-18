package at.ac.fhvie.s24.swpj4bb.touristoffice.demo.business.entity;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {
    public static List<Occupancy> convertExcelToListOfOccupancy(InputStream is) {
        List<Occupancy> occupancies = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Occupancy occupancy = new Occupancy();


                occupancy.setOccupancyid((int) row.getCell(0).getNumericCellValue());
                occupancy.setRooms((int) row.getCell(1).getNumericCellValue());
                occupancy.setUsedrooms((int) row.getCell(2).getNumericCellValue());
                occupancy.setBeds((int) row.getCell(3).getNumericCellValue());
                occupancy.setUsedbeds((int) row.getCell(4).getNumericCellValue());
                occupancy.setYear((int) row.getCell(5).getNumericCellValue());
                occupancy.setMonth((int) row.getCell(6).getNumericCellValue());

                occupancies.add(occupancy);
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return occupancies;
    }
}
