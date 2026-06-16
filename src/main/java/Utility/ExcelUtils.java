package Utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ExcelUtils - Reads test data from .xlsx files using Apache POI.
 * Place your Excel file under: src/test/resources/testdata/
 */
public class ExcelUtils {

    /**
     * Returns test data as Object[][] for use with TestNG @DataProvider.
     * Each row becomes one Object[] = { username, password, expectedResult }
     *
     * @param filePath  full path to .xlsx file
     * @param sheetName sheet to read
     * @return Object[][] for @DataProvider
     */
    public static Object[][] getTestDataAsArray(String filePath, String sheetName) {
        List<Object[]> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Object[] rowData = new Object[colCount];
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    rowData[j] = (cell != null) ? cell.toString().trim() : "";
                }
                dataList.add(rowData);
            }

        } catch (IOException e) {
            LogUtils.error("ExcelUtils error: " + e.getMessage());
        }

        return dataList.toArray(new Object[0][]);
    }

    /**
     * Returns test data as List of Maps (column header → cell value).
     * Useful when you want to access data by column name.
     */
    public static List<Map<String, String>> getTestData(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < colCount; j++) {
                    String header = headerRow.getCell(j).getStringCellValue().trim();
                    Cell cell = row.getCell(j);
                    rowData.put(header, (cell != null) ? cell.toString().trim() : "");
                }
                dataList.add(rowData);
            }

        } catch (IOException e) {
            LogUtils.error("ExcelUtils error: " + e.getMessage());
        }

        return dataList;
    }
}