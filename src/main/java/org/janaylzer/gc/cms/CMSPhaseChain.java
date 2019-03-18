package org.janaylzer.gc.cms;

import org.janaylzer.gc.GCData;
import org.janaylzer.gc.cms.phase.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Tboy
 */
public class CMSPhaseChain implements PhaseChain {

    private static final List<Phase> phases = new ArrayList<>();

    static{
        phases.add(new CMSInitialMarkPhase());
        phases.add(new CMSConcurrentMarkPhase());
        phases.add(new CMSConcurrentPrecleanPhase());
        phases.add(new CMSConcurrentAbortablePrecleanPhase());
        phases.add(new CMSFinalRemarkPhase());
        phases.add(new CMSConcurrentSweepPhase());
        phases.add(new CMSConcurrentResetPhase());
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void addPhase(Phase phase){
        phases.add(phase);
    }

    public void doAction(String message, GCData data){
        for(Phase phase : phases){
            phase.action(message, data);
        }
    }
}
