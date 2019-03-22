package org.janalyzer.gc.g1;

import org.janalyzer.gc.GCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.PhaseChain;
import org.janalyzer.gc.g1.phase.*;
import org.janalyzer.util.CollectionUtils;
import org.janalyzer.util.Optional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Tboy
 */
public class G1PhaseChain implements PhaseChain {

    private static final List<GCAction> GC_PHASES = new ArrayList<>();

    static{
        GC_PHASES.add(new G1InitialMarkGCPhase());
        GC_PHASES.add(new G1RootRegionScanGCPhase());
        GC_PHASES.add(new G1ConcurrentMarkGCPhase());
        GC_PHASES.add(new G1RemarkGCPhase());
        GC_PHASES.add(new G1CleanupGCPhase());
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
