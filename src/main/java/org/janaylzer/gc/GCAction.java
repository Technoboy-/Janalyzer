package org.janaylzer.gc;

/**
 * @Author: Tboy
 */
public interface GCAction<T> {

    T action(String message);
}
