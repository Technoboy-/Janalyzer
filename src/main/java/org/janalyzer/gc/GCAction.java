package org.janalyzer.gc;

/**
 * @Author: Tboy
 */
public interface GCAction<T> {

    T action(String message);
}
