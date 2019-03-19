package org.janaylzer.gc.parallel.old;

import org.janaylzer.gc.GCAction;
import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCType;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class ParallelOldAction implements GCAction {

    public static final String PARALLEL_OLD_ACTION =
            ".*\\[Full GC\\s\\((?<" +
            PARALLEL_OLD_CAUTION +
            ">.*)\\)\\s\\["+
            PARALLEL_SCAVENGE +
            ":\\s(?<" +
            YOUNG_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            YOUNG_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            YOUNG_SIZE +
            ">\\d+\\w)\\)\\]" +
            "\\s\\[" +
            PARALLEL_OLD +
            ":\\s(?<" +
            OLD_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            OLD_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            OLD_SIZE +
            ">\\d+\\w)\\)\\]" +
            "\\s(?<" +
            HEAP_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),((\\s)|(\\s\\[Metaspace.*\\],\\s))(?<" +
            PARALLEL_OLD_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]";

    private static final Pattern PARALLEL_OLD_PATTERN = Pattern.compile(PARALLEL_OLD_ACTION);

    @Override
    public void action(String message, GCData data) {
        if(!message.contains(PARALLEL_SCAVENGE) || !message.contains(PARALLEL_OLD)){
            return;
        }
        Matcher matcher = PARALLEL_OLD_PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }
        String caution;
        if (StringUtils.isNotEmpty(caution = matcher.group(PARALLEL_OLD_CAUTION))) {
            data.addProperties(PARALLEL_OLD_CAUTION, caution);
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
        String oldUsageBefore;
        if (StringUtils.isNotEmpty(oldUsageBefore = matcher.group(OLD_USAGE_BEFORE))) {
            data.addProperties(OLD_USAGE_BEFORE, oldUsageBefore);
        }
        String oldUsageAfter;
        if (StringUtils.isNotEmpty(oldUsageAfter = matcher.group(OLD_USAGE_AFTER))) {
            data.addProperties(OLD_USAGE_AFTER, oldUsageAfter);
        }
        String oldSize;
        if (StringUtils.isNotEmpty(oldSize = matcher.group(OLD_SIZE))) {
            data.addProperties(OLD_SIZE, oldSize);
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
        if (StringUtils.isNotEmpty(duration = matcher.group(PARALLEL_OLD_DURATION))) {
            data.addProperties(PARALLEL_OLD_DURATION, duration);
        }
        //
        data.setType(GCType.PARALLEL_OLD);
    }
}
