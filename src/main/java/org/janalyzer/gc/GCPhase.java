package org.janalyzer.gc;


import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: Tboy
 */
public class GCPhase implements Serializable {

    private String phase;

    private Map<String, String> properties = new TreeMap<>();

    public GCPhase() {
        //NOP
    }

    public GCPhase(String phase) {
        this.phase = phase;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addProperties(String key, String value) {
        this.properties.put(key, value);
    }

    @Override
    public String toString() {
        return "GCPhase{" +
                "phase=" + phase +
                ", properties=" + properties +
                '}';
    }
}
