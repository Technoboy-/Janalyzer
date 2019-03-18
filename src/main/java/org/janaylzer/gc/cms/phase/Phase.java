package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCPhase;

/**
 * @Author: Tboy
 */
public interface Phase {

    void action(String message, GCData data);

    GCPhase name();
}
