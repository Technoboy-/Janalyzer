package org.janalyzer.gc.parallel.scavenge;

import org.janalyzer.gc.*;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class ParallelScavengeGCAction extends CommonGCAction {

    public static final String PARALLEL_SCAVENGE_ACTION =
            ".*\\[GC\\s\\((?<" +
            PARALLEL_SCAVENGE_CAUTION +
            ">.*)\\)\\s\\[" +
            PARALLEL_SCAVENGE +
            ":\\s(?<" +
            YOUNG_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            YOUNG_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            YOUNG_SIZE +
            ">\\d+\\w)\\)\\]\\s(?<" +
            HEAP_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            PARALLEL_SCAVENGE_DURATION +
            ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern PARALLEL_SCAVENGE_PATTERN = Pattern.compile(PARALLEL_SCAVENGE_ACTION);


    @Override
    public boolean match(String message) {
        if(!message.contains(PARALLEL_SCAVENGE)){
            return false;
        }
        Matcher matcher = PARALLEL_SCAVENGE_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = PARALLEL_SCAVENGE_PATTERN.matcher(message);
        matcher.find();
        //
        String caution;
        if (StringUtils.isNotEmpty(caution = matcher.group(PARALLEL_SCAVENGE_CAUTION))) {
            gcData.addProperties(PARALLEL_SCAVENGE_CAUTION, caution);
        }
        String youngUsageBefore;
        if (StringUtils.isNotEmpty(youngUsageBefore = matcher.group(YOUNG_USAGE_BEFORE))) {
            gcData.addProperties(YOUNG_USAGE_BEFORE, youngUsageBefore);
        }
        String youngUsageAfter;
        if (StringUtils.isNotEmpty(youngUsageAfter = matcher.group(YOUNG_USAGE_AFTER))) {
            gcData.addProperties(YOUNG_USAGE_AFTER, youngUsageAfter);
        }
        String youngSize;
        if (StringUtils.isNotEmpty(youngSize = matcher.group(YOUNG_SIZE))) {
            gcData.addProperties(YOUNG_SIZE, youngSize);
        }
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
        if (StringUtils.isNotEmpty(duration = matcher.group(PARALLEL_SCAVENGE_DURATION))) {
            gcData.addProperties(PARALLEL_SCAVENGE_DURATION, duration);
        }
    }

    @Override
    public GCType type() {
        return GCType.PARALLEL_SCAVENGE;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.empty();
    }
}
