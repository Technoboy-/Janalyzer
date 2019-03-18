package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCPhase;
import org.janaylzer.gc.GCType;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.CMS_CONCURRENT_ABORTABLE_PRECLEAN;
import static org.janaylzer.util.Constants.CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentAbortablePrecleanPhase implements Phase {

    public static final String CONCURRENT_ABORTABLE_PRECLEAN_PHASE = ".*" +
            CMS_CONCURRENT_ABORTABLE_PRECLEAN +
            ":\\s(?<" +
            CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CONCURRENT_ABORTABLE_PRECLEAN_PHASE_PATTERN = Pattern.compile(CONCURRENT_ABORTABLE_PRECLEAN_PHASE);

    @Override
    public void action(String message, GCData data) {
        if(!message.contains(CMS_CONCURRENT_ABORTABLE_PRECLEAN)){
            return;
        }
        Matcher matcher = CONCURRENT_ABORTABLE_PRECLEAN_PHASE_PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }
        String concurrentAbortablePrecleanDuration;
        if (StringUtils.isNotEmpty(concurrentAbortablePrecleanDuration = matcher.group(CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION))) {
            data.addProperties(CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION, concurrentAbortablePrecleanDuration);
        }
        //
        data.setType(GCType.CMS);
        data.setPhase(name());
    }

    @Override
    public GCPhase name() {
        return GCPhase.CMS_CONCURRENT_ABORTABLE_PRECLEAN;
    }


}
