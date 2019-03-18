package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCPhase;
import org.janaylzer.gc.GCType;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.CMS_CONCURRENT_RESET;
import static org.janaylzer.util.Constants.CMS_CONCURRENT_RESET_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentResetPhase implements Phase {

    public static final String CONCURRENT_RESET_PHASE = ".*" +
            CMS_CONCURRENT_RESET +
            ":\\s(?<" +
            CMS_CONCURRENT_RESET_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs\\]";

    public static final Pattern CONCURRENT_RESET_PATTERN = Pattern.compile(CONCURRENT_RESET_PHASE);

    @Override
    public void action(String message, GCData data) {
        if(!message.contains(CMS_CONCURRENT_RESET)){
            return;
        }
        Matcher matcher = CONCURRENT_RESET_PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }
        String concurrentResetDuration;
        if (StringUtils.isNotEmpty(concurrentResetDuration = matcher.group(CMS_CONCURRENT_RESET_DURATION))) {
            data.addProperties(CMS_CONCURRENT_RESET_DURATION, concurrentResetDuration);
        }
        //
        data.setType(GCType.CMS);
        data.setPhase(name());
    }

    @Override
    public GCPhase name() {
        return GCPhase.CMS_CONCURRENT_RESET;
    }

}
