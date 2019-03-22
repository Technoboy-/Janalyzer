package org.janalyzer.gc.parallel.scavenge;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCType;
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
    public Optional<GCData> action(String message) {
        if(!message.contains(PARALLEL_SCAVENGE)){
            return Optional.empty();
        }
        Matcher matcher = PARALLEL_SCAVENGE_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCData data = new GCData(GCType.PARALLEL_SCAVENGE);

        super.action(message, data);

        String caution;
        if (StringUtils.isNotEmpty(caution = matcher.group(PARALLEL_SCAVENGE_CAUTION))) {
            data.addProperties(PARALLEL_SCAVENGE_CAUTION, caution);
        }
        String youngUsageBefore;
        if (StringUtils.isNotEmpty(youngUsageBefore = matcher.group(YOUNG_USAGE_BEFORE))) {
            data.addProperties(YOUNG_USAGE_BEFORE, youngUsageBefore);
        }
        String youngUsageAfter;
        if (StringUtils.isNotEmpty(youngUsageAfter = matcher.group(YOUNG_USAGE_AFTER))) {
            data.addProperties(YOUNG_USAGE_AFTER, youngUsageAfter);
        }
        String youngSize;
        if (StringUtils.isNotEmpty(youngSize = matcher.group(YOUNG_SIZE))) {
            data.addProperties(YOUNG_SIZE, youngSize);
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
        String duration;
        if (StringUtils.isNotEmpty(duration = matcher.group(PARALLEL_SCAVENGE_DURATION))) {
            data.addProperties(PARALLEL_SCAVENGE_DURATION, duration);
        }
        //
        return Optional.of(data);
    }
}
