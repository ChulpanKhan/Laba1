package com.mycompany.laba1;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatisticsCollector {
    private final StatisticsCalculator calculator;

    public StatisticsCollector(Map<String, List<Double>> data) {
        this.calculator = new StatisticsCalculator(data);
    }
    
    public Map<String, Map<String, Double>> collectBasicStatistics() {
        Map<String, Map<String, Double>> statistics = new LinkedHashMap<>();
        statistics.put("Среднее геометрическое", calculator.calculateGeometricMean());
        statistics.put("Среднее арифметическое", calculator.calculateMean());
        statistics.put("Стандартное отклонение", calculator.calculateStandardDeviation());
        statistics.put("Размах", calculator.calculateRange());
        statistics.put("Количество элементов", calculator.calculateSize());
        statistics.put("Коэффициент вариации, %", calculator.calculateCoefOfVariance());
        statistics.put("Дисперсия", calculator.calculateVariance());
        statistics.put("Минимум", calculator.calculateMin());
        statistics.put("Максимум", calculator.calculateMax());
        statistics.put("Нижняя граница дов. инт-ла", calculator.calculateLowerBoundOfConfidenceInterval(0.95));
        statistics.put("Верхняя граница дов. инт-ла", calculator.calculateUpperBoundOfConfidenceInterval(0.95));
        return statistics;
    }
    
    public Map<String, Map<String, Double>> getCovarianceMatrix() {
        return calculator.calculateCovarianceMatrix();
    }
}


