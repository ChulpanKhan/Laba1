//что сделано:
//импорт в DataStorage
// матрица ковариации
// как сделать порядок
package com.mycompany.laba1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Controller {
    private GUIView view;
    private DataStorage dataStorage;
    private StatisticsCalculator calculator;
    
    public Controller() {
        this.view = new GUIView();  
        this.dataStorage = new DataStorage();
        view.setVisible(true);
        activateButtons();
    }

    private void activateButtons() {
        view.getImportButton().addActionListener(e -> handleImport());
        view.getExportButton().addActionListener(e -> handleExport());
        view.getExitButton().addActionListener(e -> System.exit(0));
    }

    private void handleImport() {
        File file = FileChooserView.selectInputFile();
        if (file == null) {
            JOptionPane.showMessageDialog(view, "Файл не выбран!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String[] sheetNames = ExcelReader.getSheetNames(file);
            String selectedSheet = FileChooserView.selectSheet(sheetNames);
            if (selectedSheet == null) {
                return;
            }
            
            dataStorage = ExcelReader.importData(file, selectedSheet);
            if (dataStorage.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Выбранный лист пуст!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Ошибка загрузки файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    
    }

    private void handleExport() {
//        if (FileChooserView.inputIsSelected()) {
//            JOptionPane.showMessageDialog(view, "Файл для импорта не выбран!", "Ошибка", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        File outputFile = FileChooserView.selectOutputFile();
        if (outputFile == null) {
            JOptionPane.showMessageDialog(view, "Файл не выбран!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
        }
        
        StatisticsCalculator calculator = new StatisticsCalculator(dataStorage.getData());

        Map<String, Map<String, Double>> statistics = new LinkedHashMap<>();
        statistics.put("Среднее геометрическое", calculator.calculateGeometricMean());
        statistics.put("Среднее арифметическое", calculator.calculateMean());
        statistics.put("Стандартное отклонение", calculator.calculateStandardDeviation());
        statistics.put("Размах", calculator.calculateRange());
        statistics.put("Количество элементов", calculator.calculateSize());
        statistics.put("Коэффициент вариации", calculator.calculateCoefOfVariance());
        //statistics.put("Доверительный интервал", calculator.calculateConfidenceInterval(0.95));
        statistics.put("Дисперсия", calculator.calculateVariance());
        statistics.put("Минимум", calculator.calculateMin());
        statistics.put("Максимум", calculator.calculateMax());
               

        try {
            ExcelWriter.writeStatistics(outputFile, statistics);
            System.out.println("Файл успешно сохранен: " + outputFile.getAbsolutePath());
            JOptionPane.showMessageDialog(view, "Файл успешно сохранен: " + outputFile.getAbsolutePath(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

}
