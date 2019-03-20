package org.janalyzer.gc;


import java.util.List;

/**
 * @Author: Tboy
 */
public interface PhaseChain{

    List<GCPhase> doPhase(String message);
}
