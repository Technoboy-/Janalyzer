package org.janalyzer.gc;

import java.io.Serializable;

/**
 * @Author: Tboy
 */
public class GCPhase implements Serializable {

    private String name;

    private boolean isStopTheWorld;

    public GCPhase() {
        //NOP
    }

    public GCPhase(String name, boolean isStopTheWorld) {
        this.name = name;
        this.isStopTheWorld = isStopTheWorld;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStopTheWorld() {
        return isStopTheWorld;
    }

    public void setStopTheWorld(boolean stopTheWorld) {
        isStopTheWorld = stopTheWorld;
    }

    @Override
    public String toString() {
        return "GCPhase{" +
                "name=" + name +
                ", isStopTheWorld=" + isStopTheWorld +
                '}';
    }
}
