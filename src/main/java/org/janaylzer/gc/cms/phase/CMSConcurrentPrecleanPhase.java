package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCPhase;
import org.janaylzer.util.Optional;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.CMS_CONCURRENT_PRECLEAN;
import static org.janaylzer.util.Constants.CMS_CONCURRENT_PRECLEAN_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentPrecleanPhase implements Phase<Optional<GCPhase>> {

    public static final String CMS_CONCURRENT_PRECLEAN_PHASE = ".*" +
            CMS_CONCURRENT_PRECLEAN +
            ":\\s(?<" +
            CMS_CONCURRENT_PRECLEAN_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs]";

    public static final Pattern CMS_CONCURRENT_PRECLEAN_PATTERN = Pattern.compile(CMS_CONCURRENT_PRECLEAN_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(CMS_CONCURRENT_PRECLEAN)){
            return Optional.empty();
        }
        Matcher matcher = CMS_CONCURRENT_PRECLEAN_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(CMSPhase.CMS_CONCURRENT_PRECLEAN);
        //
        String concurrentPrecleanPhase;
        if (StringUtils.isNotEmpty(concurrentPrecleanPhase = matcher.group(CMS_CONCURRENT_PRECLEAN_DURATION))) {
            phase.addProperties(CMS_CONCURRENT_PRECLEAN_DURATION, concurrentPrecleanPhase);
        }
        //
        return Optional.of(phase);
    }

}
