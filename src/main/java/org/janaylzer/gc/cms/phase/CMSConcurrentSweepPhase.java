package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCPhase;
import org.janaylzer.util.Optional;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.CMS_CONCURRENT_SWEEP;
import static org.janaylzer.util.Constants.CMS_CONCURRENT_SWEEP_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentSweepPhase implements Phase<Optional<GCPhase>> {

    public static final String CONCURRENT_SWEEP_PHASE = ".*" +
            CMS_CONCURRENT_SWEEP +
            ":\\s(?<" +
            CMS_CONCURRENT_SWEEP_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs\\]";

    public static final Pattern CONCURRENT_SWEEP_PATTERN = Pattern.compile(CONCURRENT_SWEEP_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(CMS_CONCURRENT_SWEEP)){
            return Optional.empty();
        }
        Matcher matcher = CONCURRENT_SWEEP_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }
        GCPhase phase = new GCPhase(CMSPhase.CMS_CONCURRENT_SWEEP);
        //
        String concurrentSweepDuration;
        if (StringUtils.isNotEmpty(concurrentSweepDuration = matcher.group(CMS_CONCURRENT_SWEEP_DURATION))) {
            phase.addProperties(CMS_CONCURRENT_SWEEP_DURATION, concurrentSweepDuration);
        }
        //
        return Optional.of(phase);
    }
}
