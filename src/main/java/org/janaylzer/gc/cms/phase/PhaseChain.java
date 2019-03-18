package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCData;

import java.util.List;

/**
 * @Author: Tboy
 */
public interface PhaseChain{

    List<Phase> getPhases();

    void doAction(String message, GCData data);
}
