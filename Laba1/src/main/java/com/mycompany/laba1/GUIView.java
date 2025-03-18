
package com.mycompany.laba1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GUIView extends JFrame{
    private JButton importButton;
    private JButton exportButton;
    private JButton exitButton;
    
    public GUIView() {
        setTitle("Статистический анализ XLSX");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));
        
        importButton = new JButton("Импорт данных");
        exportButton = new JButton("Экспорт результатов");
        exitButton = new JButton("Выход");
        
        add(importButton);
        add(exportButton);
        add(exitButton);
        
        setLocationRelativeTo(null);
    }
    
    public JButton getImportButton() {
        return importButton;
    }
    
    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
    
    public void getInformationPane(String text) {
        JOptionPane.showMessageDialog(this, text, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void getErrorPane(String text) {
        JOptionPane.showMessageDialog(this, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
