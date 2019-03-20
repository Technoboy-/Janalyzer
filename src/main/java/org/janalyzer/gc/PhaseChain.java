package org.janalyzer.gc;


import org.janalyzer.gc.GCPhase;

import java.util.List;

/**
 * @Author: Tboy
 */
public interface PhaseChain{

    List<GCPhase> doPhase(String message);
}
