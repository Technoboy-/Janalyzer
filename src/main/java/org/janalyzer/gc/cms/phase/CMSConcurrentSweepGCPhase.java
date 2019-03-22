package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.CMS_CONCURRENT_SWEEP;
import static org.janalyzer.util.Constants.CMS_CONCURRENT_SWEEP_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentSweepGCPhase extends CommonGCAction {

    public static final String CONCURRENT_SWEEP_PHASE = ".*" +
            CMS_CONCURRENT_SWEEP +
            ":\\s(?<" +
            CMS_CONCURRENT_SWEEP_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs\\]";

    public static final Pattern CONCURRENT_SWEEP_PATTERN = Pattern.compile(CONCURRENT_SWEEP_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(CMS_CONCURRENT_SWEEP)){
            return false;
        }
        Matcher matcher = CONCURRENT_SWEEP_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = CONCURRENT_SWEEP_PATTERN.matcher(message);
        matcher.find();
        //
        String concurrentSweepDuration;
        if (StringUtils.isNotEmpty(concurrentSweepDuration = matcher.group(CMS_CONCURRENT_SWEEP_DURATION))) {
            gcData.addProperties(CMS_CONCURRENT_SWEEP_DURATION, concurrentSweepDuration);
        }
    }

    @Override
    public GCType type() {
        return GCType.CMS;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(CMSPhase.CMS_CONCURRENT_SWEEP.name(), CMSPhase.CMS_CONCURRENT_SWEEP.isSTW()));
    }
}
