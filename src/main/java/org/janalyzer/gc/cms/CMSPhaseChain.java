package org.janalyzer.gc.cms;

import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.Phase;
import org.janalyzer.gc.PhaseChain;
import org.janalyzer.gc.cms.phase.*;
import org.janalyzer.util.Optional;

import java.util.ArrayList;
import java.util.LinkedList;
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

    @Override
    public List<GCPhase> doPhase(String message) {
        List<GCPhase> gcPhases = new LinkedList<>();
        for(Phase phaseAction : phases){
            Optional<GCPhase> phase = (Optional<GCPhase>)phaseAction.action(message);
            if(phase.isPresent()){
                gcPhases.add(phase.get());
            }
        }
        return gcPhases;
    }
}
