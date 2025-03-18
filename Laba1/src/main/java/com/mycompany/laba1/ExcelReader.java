
package com.mycompany.laba1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    
    public static String[] getSheetNames(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            int sheetCount = workbook.getNumberOfSheets();
            String[] sheetNames = new String[sheetCount];
            for (int i = 0; i < sheetCount; i++) {
                sheetNames[i] = workbook.getSheetName(i);
            }
            return sheetNames;
        }
    }
    
    public static DataStorage importData(File file, String sheetName) throws IOException {
        DataStorage storage = new DataStorage();
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IOException("Лист не найден!");            
            Iterator<Row> rowIterator = sheet.iterator();
            if (!rowIterator.hasNext()) throw new IOException("Лист пуст!");

            List<String> columnNames = getColumnNames(sheet);
           
            Map<String, List<Double>> dataMap = extractData(sheet, columnNames);  
            
            for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
                storage.addData(entry.getKey(), entry.getValue());
            }
        }
        return storage;                            
    }
    
    private static List<String> getColumnNames(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        List<String> columnNames = new ArrayList<>();
        for (Cell cell : headerRow) {
            columnNames.add(cell.getStringCellValue());
        }
        return columnNames;
    }
    
    private static Map<String, List<Double>> extractData(Sheet sheet, List<String> columnNames) {
        Map<String, List<Double>> dataMap = new HashMap<>();
        for (String column : columnNames) {
            dataMap.put(column, new ArrayList<>());
        }
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); //пропускаем заголовок
        
        FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();       
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            for (int i = 0; i < columnNames.size(); i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (cell != null) {
                    double value = getCellValue(cell, evaluator);
                    if (!Double.isNaN(value)) { // Если значение корректное, добавляем в список
                        dataMap.get(columnNames.get(i)).add(value);
                    }
                }
            }
        }
        return dataMap;
    }

    private static double getCellValue(Cell cell, FormulaEvaluator evaluator) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case FORMULA:
                CellValue evaluatedValue = evaluator.evaluate(cell);
                if (evaluatedValue.getCellType() == CellType.NUMERIC) {
                    return evaluatedValue.getNumberValue();
                }
                break;
            default:
                break;
        }
        return Double.NaN;
    }

}
