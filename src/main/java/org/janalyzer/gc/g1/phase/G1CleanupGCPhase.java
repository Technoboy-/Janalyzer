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
public class G1CleanupGCPhase extends CommonGCAction {

    private static final String CLEANUP_PHASE =
            ".*" + G1_CLEANUP +
            "\\s(?<" + HEAP_USAGE_BEFORE +
            ">\\d+\\w)->(?<" + HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" + HEAP_SIZE +
            ">\\d+\\w)\\), (?<" + G1_CLEANUP_DURATION +
            ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern CLEANUP_PATTERN = Pattern.compile(CLEANUP_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(G1_CLEANUP)){
            return false;
        }
        Matcher matcher = CLEANUP_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = CLEANUP_PATTERN.matcher(message);
        matcher.find();
        //
        String heapUsageBefore;
        if (StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))) {
            gcData.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
        }
        String heapUsageAfter;
        if (StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))) {
            gcData.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
        }
        String heapSize;
        if (StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))) {
            gcData.addProperties(HEAP_SIZE, heapSize);
        }
        String duration;
        if (StringUtils.isNotEmpty(duration = matcher.group(G1_CLEANUP_DURATION))) {
            gcData.addProperties(G1_CLEANUP_DURATION, duration);
        }
    }

    @Override
    public GCType type() {
        return GCType.G1;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(G1Phase.G1_CLEANUP.name(), G1Phase.G1_CLEANUP.isSTW()));
    }
}
