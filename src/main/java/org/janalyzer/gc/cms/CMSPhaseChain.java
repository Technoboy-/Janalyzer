package org.janalyzer.gc.cms;

import org.janalyzer.gc.GCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.PhaseChain;
import org.janalyzer.gc.cms.phase.*;
import org.janalyzer.util.CollectionUtils;
import org.janalyzer.util.Optional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Tboy
 */
public class CMSPhaseChain implements PhaseChain {

    private static final List<GCAction> GC_PHASES = new ArrayList<>();

    static{
        GC_PHASES.add(new CMSInitialMarkGCPhase());
        GC_PHASES.add(new CMSConcurrentMarkGCPhase());
        GC_PHASES.add(new CMSConcurrentPrecleanGCPhase());
        GC_PHASES.add(new CMSConcurrentAbortablePrecleanPhase());
        GC_PHASES.add(new CMSFinalRemarkGCPhase());
        GC_PHASES.add(new CMSConcurrentSweepGCPhase());
        GC_PHASES.add(new CMSConcurrentResetGCPhase());
    }

    @Override
    public Optional<List<GCData>> doPhase(String message) {
        List<GCData> gcPhases = new LinkedList<>();
        for(GCAction gc : GC_PHASES){
            Optional<GCData> phase = (Optional<GCData>) gc.action(message);
            if(phase.isPresent()){
                gcPhases.add(phase.get());
            }
        }
        return CollectionUtils.isNotEmpty(gcPhases) ? Optional.of(gcPhases) : Optional.empty();
    }
}
