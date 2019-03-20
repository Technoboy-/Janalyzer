package org.janalyzer.util;

/**
 * @Author: Tboy
 */
public class Collector<Y, O> {

    private final Y young;

    private final O old;

    public Collector(Y young, O old) {
        this.young = young;
        this.old = old;
    }

    public Y getYoung() {
        return young;
    }

    public O getOld() {
        return old;
    }
}
