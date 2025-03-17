
package com.mycompany.laba1;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class DataStorage {
    private Map<String, List<Double>> dataMap;
    
    public DataStorage() {
        this.dataMap = new LinkedHashMap<>();
    }
    
    public void addData(String columnName, List<Double> values) {
        dataMap.put(columnName, values);
    }
    
    public Map<String, List<Double>> getData() {
        return dataMap;
    }
    
    public boolean isEmpty() {
        return dataMap.isEmpty();
    }
}
