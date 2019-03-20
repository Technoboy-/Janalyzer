package org.janalyzer.util;

import java.io.Serializable;

/**
 * @Author: Tboy
 */
public class Collector<Y, O> implements Serializable {

    private Y young;

    private O old;

    public Collector() {
    }

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


    public static class All extends Collector{
        //Markable class
    }
}
