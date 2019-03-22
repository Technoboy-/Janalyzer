package org.janalyzer.gc;


import org.janalyzer.util.Optional;

import java.util.List;

/**
 * @Author: Tboy
 */
public interface PhaseChain{

    Optional<List<GCData>> doPhase(String message);
}
