package com.mycompany.laba1;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserView {
    
    static File inputFile = null;

    public static File selectInputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите XLSX файл");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
             inputFile = fileChooser.getSelectedFile();
            return inputFile;
        }
        return inputFile; // Если файл не выбран
    }
    
    public static boolean inputIsSelected() {
        return (inputFile != null);
    }

    public static String selectSheet(String[] sheetNames) {
        return (String) JOptionPane.showInputDialog(
                null,
                "Выберите лист для импорта:",
                "Выбор листа",
                JOptionPane.QUESTION_MESSAGE,
                null,
                sheetNames,
                sheetNames[0]
        );
    }

    public static File selectOutputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите место для сохранения XLSX");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                selectedFile = new File(filePath + ".xlsx");
            }
            return selectedFile;
        }
        return null;
    }
}
