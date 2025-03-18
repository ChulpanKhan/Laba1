
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
    
    public static void writeStatistics(File outputFile, Map<String, Map<String, Double>> statistics,
            Map<String, Map<String, Double>> covarianceMatrix) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Статистика");
            int rowIndex = 0;
            rowIndex = writeStatisticsData(sheet, statistics);
            rowIndex++;
            writeCovarianceMatrix(sheet, covarianceMatrix, rowIndex);
            for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
                sheet.autoSizeColumn(i); //подгон ширины столбца
            }
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                workbook.write(fos);
            }
        }
    }

    private static int writeStatisticsData(Sheet sheet, Map<String, Map<String, Double>> statistics) {
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
        return rowIndex;
    }
    
    private static void writeCovarianceMatrix(Sheet sheet, Map<String, Map<String, Double>> covarianceMatrix, int rowIndex) {
        List<String> variables = new ArrayList<>(covarianceMatrix.keySet());

        Row headerRow = sheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Матрица ковариации");
        for (int i = 0; i < variables.size(); i++) {
            headerRow.createCell(i + 1).setCellValue(variables.get(i));
        }
        for (int i = 0; i < variables.size(); i++) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(variables.get(i));
            for (int j = 0; j < variables.size(); j++) {
                row.createCell(j + 1).setCellValue(covarianceMatrix.get(variables.get(i)).get(variables.get(j)));
            }
        }
    }   
}
