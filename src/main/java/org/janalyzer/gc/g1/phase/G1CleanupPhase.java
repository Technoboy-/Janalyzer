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
public class G1CleanupPhase implements Phase<Optional<GCPhase>> {

    private static final String CLEANUP_PHASE =
            ".*" + G1_CLEANUP +
            "\\s(?<" + HEAP_USAGE_BEFORE +
            ">\\d+\\w)->(?<" + HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" + HEAP_SIZE +
            ">\\d+\\w)\\), (?<" + G1_CLEANUP_DURATION +
            ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern CLEANUP_PATTERN = Pattern.compile(CLEANUP_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(G1_CLEANUP)){
            return Optional.empty();
        }
        Matcher matcher = CLEANUP_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(name());

        String heapUsageBefore;
        if (StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))) {
            phase.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
        }
        String heapUsageAfter;
        if (StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))) {
            phase.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
        }
        String heapSize;
        if (StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))) {
            phase.addProperties(HEAP_SIZE, heapSize);
        }
        String duration;
        if (StringUtils.isNotEmpty(duration = matcher.group(G1_CLEANUP_DURATION))) {
            phase.addProperties(G1_CLEANUP_DURATION, duration);
        }
        //
        return Optional.of(phase);
    }

    @Override
    public String name() {
        return G1Phase.G1_CLEANUP.name();
    }
}
