package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.Phase;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.CMS_CONCURRENT_MARK;
import static org.janalyzer.util.Constants.CMS_CONCURRENT_MARK_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentMarkPhase implements Phase<Optional<GCPhase>> {

    public static final String CONCURRENT_MARK_PHASE = ".*" +
            CMS_CONCURRENT_MARK +
            ":\\s(?<" +
            CMS_CONCURRENT_MARK_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CONCURRENT_MARK_PATTERN = Pattern.compile(CONCURRENT_MARK_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(CMS_CONCURRENT_MARK)){
            return Optional.empty();
        }
        Matcher matcher = CONCURRENT_MARK_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(name());
        //
        String concurrentMarkDuration;
        if (StringUtils.isNotEmpty(concurrentMarkDuration = matcher.group(CMS_CONCURRENT_MARK_DURATION))) {
            phase.addProperties(CMS_CONCURRENT_MARK_DURATION, concurrentMarkDuration);
        }
        //
        return Optional.of(phase);
    }

    @Override
    public String name() {
        return CMSPhase.CMS_CONCURRENT_MARK.name();
    }
}
