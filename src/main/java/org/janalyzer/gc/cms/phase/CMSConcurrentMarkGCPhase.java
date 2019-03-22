package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.CMS_CONCURRENT_MARK;
import static org.janalyzer.util.Constants.CMS_CONCURRENT_MARK_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentMarkGCPhase extends CommonGCAction {

    public static final String CONCURRENT_MARK_PHASE = ".*" +
            CMS_CONCURRENT_MARK +
            ":\\s(?<" +
            CMS_CONCURRENT_MARK_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CONCURRENT_MARK_PATTERN = Pattern.compile(CONCURRENT_MARK_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(CMS_CONCURRENT_MARK)){
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
        if (StringUtils.isNotEmpty(concurrentMarkDuration = matcher.group(CMS_CONCURRENT_MARK_DURATION))) {
            gcData.addProperties(CMS_CONCURRENT_MARK_DURATION, concurrentMarkDuration);
        }
    }

    @Override
    public GCType type() {
        return GCType.CMS;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(CMSPhase.CMS_CONCURRENT_MARK.name(), CMSPhase.CMS_CONCURRENT_MARK.isSTW()));
    }
}
