package org.janalyzer.gc.parnew;

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
public class ParNewGCAction extends CommonGCAction {

    public static final String PARNEW_ACTION = ".*" +
            "((\\[GC\\s\\((?<" +
            PARNEW_CAUTION +
            ">[\\w ]+)\\).*?\\[)|(\\[GC.*?\\[))"+
            PARNEW +
            "((:\\s)|(.*\\]:\\s))(?<" +
            YOUNG_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            YOUNG_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            YOUNG_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            PARNEW_CLEANUP_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]((\\s)|(.*\\[CMS.*\\ssecs\\]\\s))(?<" +
            HEAP_USAGE_BEFORE +
            ">\\d+\\w)->(?<" +
            HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),((\\s)|(\\s\\[Metaspace.*\\],\\s))(?<" +
            PARNEW_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]";

    private static final Pattern PARNEW_PATTERN = Pattern.compile(PARNEW_ACTION);

    @Override
    public Optional<GCData> action(String message){
        if(!message.contains(PARNEW)){
            return Optional.empty();
        }
        Matcher matcher = PARNEW_PATTERN.matcher(message);
        if(!matcher.find()){
            return Optional.empty();
        }

        GCData data = new GCData(GCType.PARNEW);

        super.action(message, data);

        String caution;
        if(StringUtils.isNotEmpty(caution = matcher.group(PARNEW_CAUTION))){
            data.addProperties(PARNEW_CAUTION, caution);
        }
        String youngUsageBefore;
        if(StringUtils.isNotEmpty(youngUsageBefore = matcher.group(YOUNG_USAGE_BEFORE))){
            data.addProperties(YOUNG_USAGE_BEFORE, youngUsageBefore);
        }
        String youngUsageAfter;
        if(StringUtils.isNotEmpty(youngUsageAfter = matcher.group(YOUNG_USAGE_AFTER))){
            data.addProperties(YOUNG_USAGE_AFTER, youngUsageAfter);
        }
        String youngSize;
        if(StringUtils.isNotEmpty(youngSize = matcher.group(YOUNG_SIZE))){
            data.addProperties(YOUNG_SIZE, youngSize);
        }
        String cleanupDuration;
        if(StringUtils.isNotEmpty(cleanupDuration = matcher.group(PARNEW_CLEANUP_DURATION))){
            data.addProperties(PARNEW_CLEANUP_DURATION, cleanupDuration);
        }
        String heapUsageBefore;
        if(StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))){
            data.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
        }
        String heapUsageAfter;
        if(StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))){
            data.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
        }
        String heapSize;
        if(StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))){
            data.addProperties(HEAP_SIZE, heapSize);
        }
        String duration;
        if(StringUtils.isNotEmpty(duration = matcher.group(PARNEW_DURATION))){
            data.addProperties(PARNEW_DURATION, duration);
        }
        //
        return Optional.of(data);
    }
}
