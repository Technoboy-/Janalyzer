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
public class G1ConcurrentMarkPhase implements Phase<Optional<GCPhase>> {

    private static final String CONCURRENT_MARK_PHASE =
            ".*\\[GC\\s" + G1_CONCURRENT_MARK +
            ",\\s(?<" + G1_CONCURRENT_MARK_DURATION + ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern CONCURRENT_MARK_PATTERN = Pattern.compile(CONCURRENT_MARK_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(G1_CONCURRENT_MARK)){
            return Optional.empty();
        }
        Matcher matcher = CONCURRENT_MARK_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(name());

        String concurrentMarkDuration;
        if (StringUtils.isNotEmpty(concurrentMarkDuration = matcher.group(G1_CONCURRENT_MARK_DURATION))) {
            phase.addProperties(G1_CONCURRENT_MARK_DURATION, concurrentMarkDuration);
        }
        //
        return Optional.of(phase);
    }

    @Override
    public String name() {
        return G1Phase.G1_CONCURRENT_MARK.name();
    }
}
