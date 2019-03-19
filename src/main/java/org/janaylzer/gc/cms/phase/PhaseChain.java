package org.janaylzer.gc.cms.phase;


import org.janaylzer.gc.GCPhase;

import java.util.List;

/**
 * @Author: Tboy
 */
public interface PhaseChain{

    List<GCPhase> doPhase(String message);
}
