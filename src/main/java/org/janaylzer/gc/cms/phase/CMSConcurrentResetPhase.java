package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCPhase;
import org.janaylzer.util.Optional;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.CMS_CONCURRENT_RESET;
import static org.janaylzer.util.Constants.CMS_CONCURRENT_RESET_DURATION;

/**
 * @Author: Tboy
 */
public class CMSConcurrentResetPhase implements Phase<Optional<GCPhase>> {

    public static final String CONCURRENT_RESET_PHASE = ".*" +
            CMS_CONCURRENT_RESET +
            ":\\s(?<" +
            CMS_CONCURRENT_RESET_DURATION +
            ">\\d+\\.\\d+)/\\d+\\.\\d+\\ssecs\\]";

    public static final Pattern CONCURRENT_RESET_PATTERN = Pattern.compile(CONCURRENT_RESET_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(CMS_CONCURRENT_RESET)){
            return Optional.empty();
        }
        Matcher matcher = CONCURRENT_RESET_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(CMSPhase.CMS_CONCURRENT_RESET);
        //
        String concurrentResetDuration;
        if (StringUtils.isNotEmpty(concurrentResetDuration = matcher.group(CMS_CONCURRENT_RESET_DURATION))) {
            phase.addProperties(CMS_CONCURRENT_RESET_DURATION, concurrentResetDuration);
        }
        //
        return Optional.of(phase);
    }

}
