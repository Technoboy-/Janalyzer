package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCPhase;
import org.janaylzer.gc.GCType;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class CMSConcurrentPrecleanPhase implements Phase {

    public static final String CMS_CONCURRENT_PRECLEAN_PHASE = ".*" +
            CMS_CONCURRENT_PRECLEAN +
            ":\\s(?<" +
            CMS_CONCURRENT_PRECLEAN_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CMS_CONCURRENT_PRECLEAN_PATTERN = Pattern.compile(CMS_CONCURRENT_PRECLEAN_PHASE);

    @Override
    public void action(String message, GCData data) {
        if(!message.contains(CMS_CONCURRENT_PRECLEAN)){
            return;
        }
        Matcher matcher = CMS_CONCURRENT_PRECLEAN_PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }

        String concurrentPrecleanPhase;
        if (StringUtils.isNotEmpty(concurrentPrecleanPhase = matcher.group(CMS_CONCURRENT_PRECLEAN_DURATION))) {
            data.addProperties(CMS_CONCURRENT_PRECLEAN_DURATION, concurrentPrecleanPhase);
        }
        //
        data.setType(GCType.CMS);
        data.setPhase(name());
    }

    @Override
    public GCPhase name() {
        return GCPhase.CMS_CONCURRENT_PRECLEAN;
    }

}
