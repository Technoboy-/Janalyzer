package org.janalyzer.gc;

import java.io.Serializable;

/**
 * @Author: Tboy
 */
public class GCTime implements Serializable {

    private String user;

    private String sys;

    private String real;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        this.real = real;
    }
}
