package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.CMS_CONCURRENT_ABORTABLE_PRECLEAN;
import static org.janalyzer.util.Constants.CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentAbortablePrecleanPhase extends CommonGCAction {

    public static final String CONCURRENT_ABORTABLE_PRECLEAN_PHASE = ".*" +
            CMS_CONCURRENT_ABORTABLE_PRECLEAN +
            ":\\s(?<" +
            CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CONCURRENT_ABORTABLE_PRECLEAN_PHASE_PATTERN = Pattern.compile(CONCURRENT_ABORTABLE_PRECLEAN_PHASE);

    @Override
    public boolean match(String message) {
        if(!message.contains(CMS_CONCURRENT_ABORTABLE_PRECLEAN)){
            return false;
        }
        Matcher matcher = CONCURRENT_ABORTABLE_PRECLEAN_PHASE_PATTERN.matcher(message);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = CONCURRENT_ABORTABLE_PRECLEAN_PHASE_PATTERN.matcher(message);
        //
        String concurrentAbortablePrecleanDuration;
        if (StringUtils.isNotEmpty(concurrentAbortablePrecleanDuration = matcher.group(CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION))) {
            gcData.addProperties(CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION, concurrentAbortablePrecleanDuration);
        }
    }

    @Override
    public GCType type() {
        return GCType.CMS;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(CMSPhase.CMS_CONCURRENT_ABORTABLE_PRECLEAN.name(), CMSPhase.CMS_CONCURRENT_ABORTABLE_PRECLEAN.isSTW()));
    }
}
