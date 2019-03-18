package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCPhase;
import org.janaylzer.gc.GCType;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.CMS_CONCURRENT_SWEEP;
import static org.janaylzer.util.Constants.CMS_CONCURRENT_SWEEP_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentSweepPhase implements Phase {

    public static final String CONCURRENT_SWEEP_PHASE = ".*" +
            CMS_CONCURRENT_SWEEP +
            ":\\s(?<" +
            CMS_CONCURRENT_SWEEP_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs\\]";

    public static final Pattern CONCURRENT_SWEEP_PATTERN = Pattern.compile(CONCURRENT_SWEEP_PHASE);

    @Override
    public void action(String message, GCData data) {
        if(!message.contains(CMS_CONCURRENT_SWEEP)){
            return;
        }
        Matcher matcher = CONCURRENT_SWEEP_PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }
        String concurrentSweepDuration;
        if (StringUtils.isNotEmpty(concurrentSweepDuration = matcher.group(CMS_CONCURRENT_SWEEP_DURATION))) {
            data.addProperties(CMS_CONCURRENT_SWEEP_DURATION, concurrentSweepDuration);
        }
        //
        data.setType(GCType.CMS);
        data.setPhase(name());
    }

    @Override
    public GCPhase name() {
        return GCPhase.CMS_CONCURRENT_SWEEP;
    }
}
