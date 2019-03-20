package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.Phase;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.CMS_CONCURRENT_ABORTABLE_PRECLEAN;
import static org.janalyzer.util.Constants.CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentAbortablePrecleanPhase implements Phase<Optional<GCPhase>> {

    public static final String CONCURRENT_ABORTABLE_PRECLEAN_PHASE = ".*" +
            CMS_CONCURRENT_ABORTABLE_PRECLEAN +
            ":\\s(?<" +
            CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CONCURRENT_ABORTABLE_PRECLEAN_PHASE_PATTERN = Pattern.compile(CONCURRENT_ABORTABLE_PRECLEAN_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(CMS_CONCURRENT_ABORTABLE_PRECLEAN)){
            return Optional.empty();
        }
        Matcher matcher = CONCURRENT_ABORTABLE_PRECLEAN_PHASE_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(name());
        //
        String concurrentAbortablePrecleanDuration;
        if (StringUtils.isNotEmpty(concurrentAbortablePrecleanDuration = matcher.group(CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION))) {
            phase.addProperties(CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION, concurrentAbortablePrecleanDuration);
        }

        return Optional.of(phase);
    }

    @Override
    public String name() {
        return CMSPhase.CMS_CONCURRENT_ABORTABLE_PRECLEAN.name();
    }

}
