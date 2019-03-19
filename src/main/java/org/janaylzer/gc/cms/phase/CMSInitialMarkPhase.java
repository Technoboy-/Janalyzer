package org.janaylzer.gc.cms.phase;

import org.janaylzer.gc.GCPhase;
import org.janaylzer.util.Optional;
import org.janaylzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class CMSInitialMarkPhase implements Phase<Optional<GCPhase>> {

    public static final String INITIAL_MARK_PHASE = ".*" +
            CMS_INITIAL_MARK +
            ":\\s(?<" +
            OLD_USAGE_BEFORE +
            ">\\d+\\w)\\((?<" +
            OLD_SIZE +
            ">\\d+\\w)\\)\\]\\s(?<" +
            HEAP_USAGE_BEFORE +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            CMS_INITIAL_MARK_DURATION +
            ">\\d+\\.\\d+)\\ssecs\\]";

    public static final Pattern INITIAL_MARK_PATTERN = Pattern.compile(INITIAL_MARK_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(CMS_INITIAL_MARK)){
            return Optional.empty();
        }
        Matcher matcher = INITIAL_MARK_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(CMSPhase.CMS_INITIAL_MARK);
        //
        String cmsOldUsageBefore;
        if (StringUtils.isNotEmpty(cmsOldUsageBefore = matcher.group(OLD_USAGE_BEFORE))) {
            phase.addProperties(OLD_USAGE_BEFORE, cmsOldUsageBefore);
        }

        String cmsOldSize;
        if (StringUtils.isNotEmpty(cmsOldSize = matcher.group(OLD_SIZE))) {
            phase.addProperties(OLD_SIZE, cmsOldSize);
        }

        String heapUsageBefore;
        if (StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))) {
            phase.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
        }

        String heapSize;
        if (StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))) {
            phase.addProperties(HEAP_SIZE, heapSize);
        }

        String initialMarkDuration;
        if (StringUtils.isNotEmpty(initialMarkDuration = matcher.group(CMS_INITIAL_MARK_DURATION))) {
            phase.addProperties(CMS_INITIAL_MARK_DURATION, initialMarkDuration);
        }

        return Optional.of(phase);
    }
}
