package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCPhase;
import org.janaylzer.gc.GCType;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.CMS_CONCURRENT_MARK;
import static org.janaylzer.util.Constants.CMS_CONCURRENT_MARK_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentMarkPhase implements Phase {

    public static final String CONCURRENT_MARK_PHASE = ".*" +
            CMS_CONCURRENT_MARK +
            ":\\s(?<" +
            CMS_CONCURRENT_MARK_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CONCURRENT_MARK_PATTERN = Pattern.compile(CONCURRENT_MARK_PHASE);

    @Override
    public void action(String message, GCData data) {
        if(!message.contains(CMS_CONCURRENT_MARK)){
            return;
        }
        Matcher matcher = CONCURRENT_MARK_PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }
        String concurrentMarkDuration;
        if (StringUtils.isNotEmpty(concurrentMarkDuration = matcher.group(CMS_CONCURRENT_MARK_DURATION))) {
            data.addProperties(CMS_CONCURRENT_MARK_DURATION, concurrentMarkDuration);
        }
        //
        data.setType(GCType.CMS);
        data.setPhase(name());
    }

    @Override
    public GCPhase name() {
        return GCPhase.CMS_CONCURRENT_MARK;
    }

}
