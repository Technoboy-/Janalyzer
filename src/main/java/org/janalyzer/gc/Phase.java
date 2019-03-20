package org.janalyzer.gc;

/**
 * @Author: Tboy
 */
public interface Phase<T> {

    T action(String message);

    String name();
}
