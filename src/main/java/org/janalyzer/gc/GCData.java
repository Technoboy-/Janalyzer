package org.janalyzer.gc;


import org.janalyzer.util.Optional;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: Tboy
 */
public class GCData implements Serializable {

    private String datetime;

    private GCType type;

    private Optional<GCPhase> phase;

    private GCTime gcTime;

    private Map<String, String> properties = new TreeMap<>();


    public GCData() {
        //NOP
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public GCTime getGcTime() {
        return gcTime;
    }

    public void setGcTime(GCTime gcTime) {
        this.gcTime = gcTime;
    }

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

    public Optional<GCPhase> getPhase() {
        return phase;
    }

    public void setPhase(Optional<GCPhase> phase) {
        this.phase = phase;
    }

    @Override
    public String toString() {
        return "GCData{" +
                "datetime=" + datetime +
                ", type=" + type +
                ", phase=" + phase +
                ", gcTime=" + gcTime +
                ", properties=" + properties +
                '}';
    }
}
