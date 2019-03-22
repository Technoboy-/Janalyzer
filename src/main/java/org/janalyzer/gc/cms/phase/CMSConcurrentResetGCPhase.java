package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.CMS_CONCURRENT_RESET;
import static org.janalyzer.util.Constants.CMS_CONCURRENT_RESET_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentResetGCPhase extends CommonGCAction {

    public static final String CONCURRENT_RESET_PHASE = ".*" +
            CMS_CONCURRENT_RESET +
            ":\\s(?<" +
            CMS_CONCURRENT_RESET_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs\\]";

    public static final Pattern CONCURRENT_RESET_PATTERN = Pattern.compile(CONCURRENT_RESET_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(CMS_CONCURRENT_RESET)){
            return false;
        }
        Matcher matcher = CONCURRENT_RESET_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        Matcher matcher = CONCURRENT_RESET_PATTERN.matcher(message);
        matcher.find();
        //
        String concurrentResetDuration;
        if (StringUtils.isNotEmpty(concurrentResetDuration = matcher.group(CMS_CONCURRENT_RESET_DURATION))) {
            gcData.addProperties(CMS_CONCURRENT_RESET_DURATION, concurrentResetDuration);
        }
    }

    @Override
    public GCType type() {
        return GCType.CMS;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(CMSPhase.CMS_CONCURRENT_RESET.name(), CMSPhase.CMS_CONCURRENT_RESET.isSTW()));
    }
}
