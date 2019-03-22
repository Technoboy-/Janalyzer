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
public class SerialGCAction extends CommonGCAction {

    public static final String SERIAL_ACTION =
            ".*\\[GC\\s\\((?<" +
            SERIAL_CAUTION +
            ">.*)\\).*\\["+
            SERIAL +
            ":\\s(?<" +
            YOUNG_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            YOUNG_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            YOUNG_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            SERIAL_YOUNG_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]" +
            "\\s(?<" +
            HEAP_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            SERIAL_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]";

    private static final Pattern SERIAL_PATTERN = Pattern.compile(SERIAL_ACTION);

    @Override
    public boolean match(String message) {
        if(!message.contains(SERIAL)){
            return false;
        }
        Matcher matcher = SERIAL_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = SERIAL_PATTERN.matcher(message);
        matcher.find();
        //
        String caution;
        if (StringUtils.isNotEmpty(caution = matcher.group(SERIAL_CAUTION))) {
            gcData.addProperties(SERIAL_CAUTION, caution);
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
        String youngDuration;
        if (StringUtils.isNotEmpty(youngDuration = matcher.group(SERIAL_YOUNG_DURATION))) {
            gcData.addProperties(SERIAL_YOUNG_DURATION, youngDuration);
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
        if (StringUtils.isNotEmpty(duration = matcher.group(SERIAL_DURATION))) {
            gcData.addProperties(SERIAL_DURATION, duration);
        }
    }

    @Override
    public GCType type() {
        return GCType.SERIAL;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.empty();
    }

}
