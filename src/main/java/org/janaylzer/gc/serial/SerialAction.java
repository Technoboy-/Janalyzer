package org.janaylzer.gc.serial;

import org.janaylzer.gc.GCAction;
import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCType;
import org.janaylzer.util.Optional;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class SerialAction implements GCAction<Optional<GCData>> {

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
    public Optional<GCData> action(String message) {
        if(!message.contains(SERIAL)){
            return Optional.empty();
        }
        Matcher matcher = SERIAL_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCData data = new GCData(GCType.SERIAL);

        String caution;
        if (StringUtils.isNotEmpty(caution = matcher.group(SERIAL_CAUTION))) {
            data.addProperties(SERIAL_CAUTION, caution);
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
        String youngDuration;
        if (StringUtils.isNotEmpty(youngDuration = matcher.group(SERIAL_YOUNG_DURATION))) {
            data.addProperties(SERIAL_YOUNG_DURATION, youngDuration);
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
        if (StringUtils.isNotEmpty(duration = matcher.group(SERIAL_DURATION))) {
            data.addProperties(SERIAL_DURATION, duration);
        }
        //
        return Optional.of(data);
    }
}
