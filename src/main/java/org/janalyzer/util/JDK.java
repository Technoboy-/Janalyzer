package org.janalyzer.util;

/**
 * @Author: Tboy
 */
public enum JDK {

    JDK8(18),

    JDK7(17),

    JDK6(16);

    private int code;

    JDK(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
