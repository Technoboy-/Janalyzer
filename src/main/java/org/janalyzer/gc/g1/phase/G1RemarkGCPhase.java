package org.janalyzer.gc.g1.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class G1RemarkGCPhase extends CommonGCAction {

    private static final String REMARK_PHASE =
            ".*\\[GC\\s" + G1_REMARK +
            ".*\\s\\[Finalize Marking,\\s(?<" + G1_FINALIZE_MARKING_DURATION + ">\\d+.\\d+)\\ssecs\\]" +
            "\\s.*\\[GC ref-proc,\\s(?<" + G1_GC_REF_PROC_DURATION + ">\\d+.\\d+)\\ssecs\\]" +
            ".*\\s\\[Unloading,\\s(?<" + G1_UNLOADING_DURATION + ">\\d+.\\d+)\\ssecs\\]" +
            ",\\s(?<" + G1_REMARK_DURATION + ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern REMARK_PATTERN = Pattern.compile(REMARK_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(G1_REMARK)){
            return false;
        }
        Matcher matcher = REMARK_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = REMARK_PATTERN.matcher(message);
        matcher.find();
        //
        String finalizeMarkingDuration;
        if (StringUtils.isNotEmpty(finalizeMarkingDuration = matcher.group(G1_FINALIZE_MARKING_DURATION))) {
            gcData.addProperties(G1_FINALIZE_MARKING_DURATION, finalizeMarkingDuration);
        }
        String gcRefProcDuration;
        if (StringUtils.isNotEmpty(gcRefProcDuration = matcher.group(G1_GC_REF_PROC_DURATION))) {
            gcData.addProperties(G1_GC_REF_PROC_DURATION, gcRefProcDuration);
        }
        String unloadingDuration;
        if (StringUtils.isNotEmpty(unloadingDuration = matcher.group(G1_UNLOADING_DURATION))) {
            gcData.addProperties(G1_UNLOADING_DURATION, unloadingDuration);
        }
        String duration;
        if (StringUtils.isNotEmpty(duration = matcher.group(G1_REMARK_DURATION))) {
            gcData.addProperties(G1_REMARK_DURATION, duration);
        }
    }

    @Override
    public GCType type() {
        return GCType.G1;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(G1Phase.G1_REMARK.name(), G1Phase.G1_REMARK.isSTW()));
    }
}
