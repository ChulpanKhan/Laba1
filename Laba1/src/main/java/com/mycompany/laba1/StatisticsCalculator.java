//+1. среднее геометрическое для каждой выборки
//+2. среднее арифметическое для каждой выборки
//+3. оценку стандартного отклонения для каждой выборки
//+4. размах каждой выборки
//-5. коэффициенты ковариации для всех пар случайных чисел
//+6. количество элементов в каждой выборке
//+7. коэффициент вариации для каждой выборки
//+8. для каждой выборки построить доверительный интервал для мат. ожидания 
//   (Предполагая, что случайные числа подчиняются нормальному закону распределения)
//+9. оценку дисперсии для каждой выборки
//+10. максимумы и минимумы для каждой выборки
package com.mycompany.laba1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.linear.RealMatrix;

public class StatisticsCalculator {
    private final Map<String, DescriptiveStatistics> statisticsMap = new HashMap<>();
    
    public StatisticsCalculator(Map<String, List<Double>> data) {
        for (Map.Entry<String, List<Double>> entry : data.entrySet()) {
            DescriptiveStatistics stats = new DescriptiveStatistics();
            for (double value : entry.getValue()) {
                stats.addValue(value);
            }
            statisticsMap.put(entry.getKey(), stats);
        }
    }
    //1. среднее геометрическое для каждой выборки
    public Map<String, Double> calculateGeometricMean() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, statisticsMap.get(key).getGeometricMean());
        }
        return results;
    }
    
    //2. среднее арифметическое для каждой выборки
    public Map<String, Double> calculateMean() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, statisticsMap.get(key).getMean());
        }
        return results;
    } 
    
    //3. оценку стандартного отклонения для каждой выборки
    public Map<String, Double> calculateStandardDeviation() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, statisticsMap.get(key).getStandardDeviation());
        }
        return results;
    } 
    
    //4. размах каждой выборки
    public Map<String, Double> calculateRange() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, statisticsMap.get(key).getMax() - statisticsMap.get(key).getMin());
        }
        return results;
    }
    
    //5. коэффициенты ковариации для всех пар случайных чисел
    public Map<String, Map<String, Double>> calculateCovarianceMatrix() {
        List<String> keys = new ArrayList<>(statisticsMap.keySet());
        int numVars = keys.size(); //кол-во переменных
        int numRows = (int)statisticsMap.get(keys.get(0)).getN(); // кол-во элеиентов

        //заполняем массив данных для RealMatrix -> getCovarianceMatrix
        double[][] dataArray = new double[numRows][numVars];
        for (int col = 0; col < numVars; col++) {
            double[] values = statisticsMap.get(keys.get(col)).getValues();
            for (int row = 0; row < numRows; row++) {
                dataArray[row][col] = values[row];
            }
        }
        Covariance covariance = new Covariance(dataArray); 
        RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
        //обратно из марицы в hashmap
        Map<String, Map<String, Double>> result = new LinkedHashMap<>();
        for (int i = 0; i < numVars; i++) {
            Map<String, Double> rowMap = new LinkedHashMap<>();
            for (int j = 0; j < numVars; j++) {
                rowMap.put(keys.get(j), covarianceMatrix.getEntry(i, j));
            }
            result.put(keys.get(i), rowMap);
        }
        return result;
    }
    
    //6. количество элементов в каждой выборке
    public Map<String, Double> calculateSize() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, (double) statisticsMap.get(key).getN());
        }
        return results;
    }

    //7. коэффициент вариации для каждой выборки
    public Map<String, Double> calculateCoefOfVariance() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, statisticsMap.get(key).getStandardDeviation()/ statisticsMap.get(key).getMean()*100);
        }
        return results;
    }

    //8. для каждой выборки построить доверительный интервал для мат. ожидания 
    //   (Предполагая, что случайные числа подчиняются нормальному закону распределения)
    public Map<String, Double> calculateUpperBoundOfConfidenceInterval(double confidenceLevel) {
        Map<String, Double> results = new HashMap<>();        
        for (String key : statisticsMap.keySet()) {
            double mean = statisticsMap.get(key).getMean();
            double marginOfError = calculateMarginOfError(statisticsMap.get(key), confidenceLevel);
            results.put(key, mean + marginOfError);
        }
        return results;
    }
    
    public Map<String, Double> calculateLowerBoundOfConfidenceInterval(double confidenceLevel) {
        Map<String, Double> results = new HashMap<>();        
        for (String key : statisticsMap.keySet()) {
            double mean = statisticsMap.get(key).getMean();
            double marginOfError = calculateMarginOfError(statisticsMap.get(key), confidenceLevel);
            results.put(key, mean - marginOfError);
        }
        return results;
    }
    
    public double calculateMarginOfError(DescriptiveStatistics stats, double confidenceLevel) {
        int n = (int) stats.getN();
        TDistribution tDist = new TDistribution(n - 1);
        double t = tDist.inverseCumulativeProbability(1 - (1 - confidenceLevel) / 2);
        double marginOfError = t*(stats.getStandardDeviation()/Math.sqrt(stats.getN()));
        return marginOfError;   
    }
 
    //9. оценку дисперсии для каждой выборки
    public Map<String, Double> calculateVariance() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, statisticsMap.get(key).getVariance());
        }
        return results;
    }
    
    //10. максимумы и минимумы для каждой выборки
    public Map<String, Double> calculateMin() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, statisticsMap.get(key).getMin());
        }
        return results;
    }
    public Map<String, Double> calculateMax() {
        Map<String, Double> results = new HashMap<>();
        for (String key : statisticsMap.keySet()) {
            results.put(key, statisticsMap.get(key).getMax());
        }
        return results;
    }
}
