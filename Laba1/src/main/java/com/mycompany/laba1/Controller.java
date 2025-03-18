
package com.mycompany.laba1;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Controller {
    private GUIView view;
    private DataStorage dataStorage;
    
    public Controller() {
        this.view = new GUIView();  
        this.dataStorage = new DataStorage();
        view.setVisible(true);
        activateButtons();
    }

    private void activateButtons() {
        view.getImportButton().addActionListener(e -> importData());
        view.getExportButton().addActionListener(e -> exportData());
        view.getExitButton().addActionListener(e -> System.exit(0));
    }

    private void importData() {
        File file = FileChooserView.selectInputFile();
        if (file == null) {
            view.getErrorPane("Файл не выбран!");
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
                view.getErrorPane("Выбранный лист пуст!");
                return;
            }
            view.getInformationPane("Файл успешно сохранен: " + file.getAbsolutePath());
        } catch (IOException e) {
            view.getErrorPane("Ошибка загрузки файла");
        }
    
    }

    private void exportData() {
        if (dataStorage.isEmpty()) {
            view.getErrorPane("Файл для импорта не выбран!");
            return;
        }
        File outputFile = FileChooserView.selectOutputFile();
        if (outputFile == null) {
            view.getErrorPane("Файл не выбран!");
            return;
        }
        
        StatisticsCollector collector = new StatisticsCollector(dataStorage.getData());
        Map<String, Map<String, Double>> statistics = collector.collectBasicStatistics();
        Map<String, Map<String, Double>> covarianceMatrix = collector.getCovarianceMatrix();

        try {
            ExcelWriter.writeStatistics(outputFile, statistics, covarianceMatrix);
            view.getInformationPane("Файл успешно сохранен: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            view.getErrorPane("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

}
