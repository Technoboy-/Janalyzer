package org.janalyzer.gc.g1.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.G1_CONCURRENT_MARK;
import static org.janalyzer.util.Constants.G1_CONCURRENT_MARK_DURATION;

/**
 * @Author: Tboy
 */
public class G1ConcurrentMarkGCPhase extends CommonGCAction {

    private static final String CONCURRENT_MARK_PHASE =
            ".*\\[GC\\s" + G1_CONCURRENT_MARK +
            ",\\s(?<" + G1_CONCURRENT_MARK_DURATION + ">\\d+.\\d+)\\ssecs\\]";

    private static final Pattern CONCURRENT_MARK_PATTERN = Pattern.compile(CONCURRENT_MARK_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(G1_CONCURRENT_MARK)){
            return false;
        }
        Matcher matcher = CONCURRENT_MARK_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = CONCURRENT_MARK_PATTERN.matcher(message);
        matcher.find();
        //
        String concurrentMarkDuration;
        if (StringUtils.isNotEmpty(concurrentMarkDuration = matcher.group(G1_CONCURRENT_MARK_DURATION))) {
            gcData.addProperties(G1_CONCURRENT_MARK_DURATION, concurrentMarkDuration);
        }
    }

    @Override
    public GCType type() {
        return GCType.G1;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(G1Phase.G1_CONCURRENT_MARK.name(), G1Phase.G1_CONCURRENT_MARK.isSTW()));
    }
}
