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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

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
    public Map<String, Double> calculateCovarianceMatrix(){
        Map<String, Double> results = new HashMap<>();
//        for (String key : statisticsMap.keySet()) {
//            results.put(key, (statisticsMap.get(key));
//        }
        return results;
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
        NormalDistribution normalDistribution = new NormalDistribution();
        double t = normalDistribution.inverseCumulativeProbability(1 - (1 - confidenceLevel) / 2);
        
        for (String key : statisticsMap.keySet()) {
            double mean = statisticsMap.get(key).getMean();
            double stdDev = statisticsMap.get(key).getStandardDeviation();
            double marginOfError = t * (stdDev / Math.sqrt(statisticsMap.get(key).getN()));
            results.put(key, mean + marginOfError);
        }
        return results;
    }
    
        public Map<String, Double> calculateLowerBoundOfConfidenceInterval(double confidenceLevel) {
        Map<String, Double> results = new HashMap<>();
        NormalDistribution normalDistribution = new NormalDistribution();
        double t = normalDistribution.inverseCumulativeProbability(1 - (1 - confidenceLevel) / 2);
        
        for (String key : statisticsMap.keySet()) {
            double mean = statisticsMap.get(key).getMean();
            double stdDev = statisticsMap.get(key).getStandardDeviation();
            double marginOfError = t * (stdDev / Math.sqrt(statisticsMap.get(key).getN()));
            results.put(key, mean - marginOfError);
        }
        return results;
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
