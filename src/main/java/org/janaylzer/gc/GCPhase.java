package org.janaylzer.gc;

import org.janaylzer.gc.cms.phase.CMSPhase;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: Tboy
 */
public class GCPhase implements Serializable {

    private CMSPhase phase;

    private Map<String, String> properties = new TreeMap<>();

    public GCPhase() {
        //NOP
    }

    public GCPhase(CMSPhase phase) {
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
