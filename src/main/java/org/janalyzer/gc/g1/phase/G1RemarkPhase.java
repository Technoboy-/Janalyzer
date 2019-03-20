package org.janalyzer.gc.g1.phase;

import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.Phase;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class G1RemarkPhase implements Phase<Optional<GCPhase>> {

    private static final String REMARK_PHASE =
            ".*\\[GC\\s" + G1_REMARK +
            ".*\\s\\[Finalize Marking,\\s(?<" + G1_FINALIZE_MARKING_DURATION + ">\\d+.\\d+)\\ssecs\\]" +
            "\\s.*\\[GC ref-proc,\\s(?<" + G1_GC_REF_PROC_DURATION + ">\\d+.\\d+)\\ssecs\\]" +
            ".*\\s\\[Unloading,\\s(?<" + G1_UNLOADING_DURATION + ">\\d+.\\d+)\\ssecs\\]" +
            ",\\s(?<" + G1_REMARK_DURATION + ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern REMARK_PATTERN = Pattern.compile(REMARK_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(G1_REMARK)){
            return Optional.empty();
        }
        Matcher matcher = REMARK_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(name());

        String finalizeMarkingDuration;
        if (StringUtils.isNotEmpty(finalizeMarkingDuration = matcher.group(G1_FINALIZE_MARKING_DURATION))) {
            phase.addProperties(G1_FINALIZE_MARKING_DURATION, finalizeMarkingDuration);
        }
        String gcRefProcDuration;
        if (StringUtils.isNotEmpty(gcRefProcDuration = matcher.group(G1_GC_REF_PROC_DURATION))) {
            phase.addProperties(G1_GC_REF_PROC_DURATION, gcRefProcDuration);
        }
        String unloadingDuration;
        if (StringUtils.isNotEmpty(unloadingDuration = matcher.group(G1_UNLOADING_DURATION))) {
            phase.addProperties(G1_UNLOADING_DURATION, unloadingDuration);
        }
        String duration;
        if (StringUtils.isNotEmpty(duration = matcher.group(G1_REMARK_DURATION))) {
            phase.addProperties(G1_REMARK_DURATION, duration);
        }
        //
        return Optional.of(phase);
    }

    @Override
    public String name() {
        return G1Phase.G1_REMARK.name();
    }
}
