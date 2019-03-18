package org.janaylzer.gc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: Tboy
 */
public class GCData implements Serializable {

    private GCType type;

    private GCPhase phase;

    private Map<String, String> properties = new TreeMap<>();

    public GCType getType() {
        return type;
    }

    public void setType(GCType type) {
        this.type = type;
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

    public GCPhase getPhase() {
        return phase;
    }

    public void setPhase(GCPhase phase) {
        this.phase = phase;
    }

    @Override
    public String toString() {
        return "GCData{" +
                "type=" + type +
                ", phase=" + phase +
                ", properties=" + properties +
                '}';
    }
}
