package org.janalyzer.gc.g1;

import org.janalyzer.gc.*;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class G1GCAction implements GCAction<Optional<GCData>> {

    private final G1FullGCAction fullGCAction = new G1FullGCAction();

    private final PhaseChain phaseChain = new G1PhaseChain();

    @Override
    public Optional<GCData> action(String message) {

        Optional<GCData> fullAction = fullGCAction.action(message);
        if(fullAction.isPresent()){
            return fullAction;
        } else{
            List<GCPhase> gcPhases = phaseChain.doPhase(message);
            GCData data = new GCData(GCType.CMS);
            data.setPhases(gcPhases);
            return Optional.of(data);
        }
    }

    static class G1FullGCAction implements GCAction<Optional<GCData>>{

        public static final String FULL_GC_ACTION =
                ".*" + FULL_GC + "\\s\\((?<" +
                G1_FULL_GC_CAUTION +
                ">[\\w ]+)\\).*,\\s(?<" +
                G1_FULL_GC_DURATION +
                ">\\d+.\\d+)\\ssecs\\]"+
                "\\s+\\[Eden:(?<" + EDEN_USAGE_BEFORE + ">\\s\\d+.\\d+\\w)\\((?<" + EDEN_SIZE + ">.*)\\)->(?<" + EDEN_USAGE_AFTER + ">\\d+.\\d+\\w)\\(.*\\)" +
                "\\sSurvivors:\\s(?<" + SURVIVOR_BEFORE + ">\\d+.\\d+\\w)->(?<" + SURVIVOR_AFTER + ">\\d+.\\d+\\w)" +
                "\\sHeap:\\s(?<" + HEAP_USAGE_BEFORE + ">\\d+\\.\\d+\\w)\\(.*\\)->(?<" + HEAP_USAGE_AFTER + ">\\d+\\.\\d+\\w)\\((?<" + HEAP_SIZE + ">\\d+.\\d+\\w)\\)\\]\\s*";

        public static final Pattern FULL_GC_PATTERN = Pattern.compile(FULL_GC_ACTION);

        @Override
        public Optional<GCData> action(String message) {
            if(!message.contains(FULL_GC)){
                return Optional.empty();
            }
            Matcher matcher = FULL_GC_PATTERN.matcher(message);
            if (!matcher.find()) {
                return Optional.empty();
            }
            GCData data = new GCData(GCType.G1);

            String caution;
            if (StringUtils.isNotEmpty(caution = matcher.group(G1_FULL_GC_CAUTION))) {
                data.addProperties(G1_FULL_GC_CAUTION, caution);
            }
            String duration;
            if (StringUtils.isNotEmpty(duration = matcher.group(G1_FULL_GC_DURATION))) {
                data.addProperties(G1_FULL_GC_DURATION, duration);
            }
            String edenUsageBefore;
            if (StringUtils.isNotEmpty(edenUsageBefore = matcher.group(EDEN_USAGE_BEFORE))) {
                data.addProperties(EDEN_USAGE_BEFORE, edenUsageBefore);
            }
            String edenSize;
            if (StringUtils.isNotEmpty(edenSize = matcher.group(EDEN_SIZE))) {
                data.addProperties(EDEN_SIZE, edenSize);
            }
            String edenUsageAfter;
            if (StringUtils.isNotEmpty(edenUsageAfter = matcher.group(EDEN_USAGE_AFTER))) {
                data.addProperties(EDEN_USAGE_AFTER, edenUsageAfter);
            }
            String survivorBefore;
            if (StringUtils.isNotEmpty(survivorBefore = matcher.group(SURVIVOR_BEFORE))) {
                data.addProperties(SURVIVOR_BEFORE, survivorBefore);
            }
            String survivorAfter;
            if (StringUtils.isNotEmpty(survivorAfter = matcher.group(SURVIVOR_AFTER))) {
                data.addProperties(SURVIVOR_AFTER, survivorAfter);
            }
            String heapUsageBefore;
            if (StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))) {
                data.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
            }
            String heapUsageAfter;
            if (StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))) {
                data.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
            }
            String heapSize;
            if (StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))) {
                data.addProperties(HEAP_SIZE, heapSize);
            }
            //
            return Optional.of(data);

        }
    }
}
