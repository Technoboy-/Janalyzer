package org.janalyzer.gc.serial;

import org.janalyzer.gc.*;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class SerialOldGCAction extends CommonGCAction {

    public static final String SERIAL_OLD_ACTION =
            ".*\\[Full GC\\s\\((?<" +
            SERIAL_OLD_CAUTION +
            ">.*)\\).*\\["+
            SERIAL_OLD +
            ":\\s(?<" +
            OLD_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            OLD_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            OLD_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            SERIAL_OLD_CLEANUP_OLD_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]" +
            "\\s(?<" +
            HEAP_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),((\\s)|(\\s\\[Metaspace.*\\],\\s))(?<" +
            SERIAL_OLD_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]";

    private static final Pattern SERIAL_OLD_PATTERN = Pattern.compile(SERIAL_OLD_ACTION);

    @Override
    public boolean match(String message) {
        if(!message.contains(SERIAL_OLD)){
            return false;
        }
        Matcher matcher = SERIAL_OLD_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = SERIAL_OLD_PATTERN.matcher(message);
        matcher.find();
        //
        String caution;
        if (StringUtils.isNotEmpty(caution = matcher.group(SERIAL_OLD_CAUTION))) {
            gcData.addProperties(SERIAL_OLD_CAUTION, caution);
        }
        String oldUsageBefore;
        if (StringUtils.isNotEmpty(oldUsageBefore = matcher.group(OLD_USAGE_BEFORE))) {
            gcData.addProperties(OLD_USAGE_BEFORE, oldUsageBefore);
        }
        String oldUsageAfter;
        if (StringUtils.isNotEmpty(oldUsageAfter = matcher.group(OLD_USAGE_AFTER))) {
            gcData.addProperties(OLD_USAGE_AFTER, oldUsageAfter);
        }
        String oldSize;
        if (StringUtils.isNotEmpty(oldSize = matcher.group(OLD_SIZE))) {
            gcData.addProperties(OLD_SIZE, oldSize);
        }
        String cleanupOldDuration;
        if (StringUtils.isNotEmpty(cleanupOldDuration = matcher.group(SERIAL_OLD_CLEANUP_OLD_DURATION))) {
            gcData.addProperties(SERIAL_OLD_CLEANUP_OLD_DURATION, cleanupOldDuration);
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
        if (StringUtils.isNotEmpty(duration = matcher.group(SERIAL_OLD_DURATION))) {
            gcData.addProperties(SERIAL_OLD_DURATION, duration);
        }
    }

    @Override
    public GCType type() {
        return GCType.SERIAL_OLD;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.empty();
    }
}
