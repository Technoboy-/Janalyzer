package org.janalyzer.gc.g1;

import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.Phase;
import org.janalyzer.gc.PhaseChain;
import org.janalyzer.gc.cms.phase.*;
import org.janalyzer.gc.g1.phase.*;
import org.janalyzer.util.Optional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Tboy
 */
public class G1PhaseChain implements PhaseChain {

    private static final List<Phase> phases = new ArrayList<>();

    static{
        phases.add(new G1InitialMarkPhase());
        phases.add(new G1RootRegionScanPhase());
        phases.add(new G1ConcurrentMarkPhase());
        phases.add(new G1RemarkPhase());
        phases.add(new G1CleanupPhase());
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
