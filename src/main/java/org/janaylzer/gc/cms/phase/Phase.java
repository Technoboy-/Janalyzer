package org.janaylzer.gc.cms.phase;

/**
 * @Author: Tboy
 */
public interface Phase<T> {

    T action(String message);

}
