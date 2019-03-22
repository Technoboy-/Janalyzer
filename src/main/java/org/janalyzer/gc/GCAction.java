package org.janalyzer.gc;


import org.janalyzer.util.Optional;

/**
 * @Author: Tboy
 */
public interface GCAction<T> {

    T action(String message);

    GCType type();

    Optional<GCPhase> phase();
}
