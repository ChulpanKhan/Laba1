/*
имя : xyz
среднее
геом мреднее
и тд
 */
package com.mycompany.laba1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
    
    public static void writeStatistics(File outputFile, Map<String, Map<String, Double>> statistics) throws IOException{
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Статистика");
            writeStatisticsData(sheet, statistics);
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                workbook.write(fos);
            }
        }
    }
    
    private static void writeStatisticsData(Sheet sheet, Map<String, Map<String, Double>> statistics) {
        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);
        List<String> columns = new ArrayList<>(statistics.values().iterator().next().keySet());
        
        headerRow.createCell(0).setCellValue("Имя параметра");
        for (int i = 0; i < columns.size(); i++) {
            headerRow.createCell(i + 1).setCellValue(columns.get(i));
        }
        
        for (Map.Entry<String, Map<String, Double>> entry : statistics.entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(entry.getKey());
            for (int i = 0; i < columns.size(); i++) {
                row.createCell(i + 1).setCellValue(entry.getValue().get(columns.get(i)));
            }
        }
    }
    
}
