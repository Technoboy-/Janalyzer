package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.CommonGCAction;
import org.janalyzer.gc.GCData;
import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.GCType;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class CMSInitialMarkGCPhase extends CommonGCAction {

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
    public boolean match(String message) {
        if(!message.contains(CMS_INITIAL_MARK)){
            return false;
        }
        Matcher matcher = INITIAL_MARK_PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = INITIAL_MARK_PATTERN.matcher(message);
        matcher.find();
        //
        String cmsOldUsageBefore;
        if (StringUtils.isNotEmpty(cmsOldUsageBefore = matcher.group(OLD_USAGE_BEFORE))) {
            gcData.addProperties(OLD_USAGE_BEFORE, cmsOldUsageBefore);
        }

        String cmsOldSize;
        if (StringUtils.isNotEmpty(cmsOldSize = matcher.group(OLD_SIZE))) {
            gcData.addProperties(OLD_SIZE, cmsOldSize);
        }

        String heapUsageBefore;
        if (StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))) {
            gcData.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
        }

        String heapSize;
        if (StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))) {
            gcData.addProperties(HEAP_SIZE, heapSize);
        }

        String initialMarkDuration;
        if (StringUtils.isNotEmpty(initialMarkDuration = matcher.group(CMS_INITIAL_MARK_DURATION))) {
            gcData.addProperties(CMS_INITIAL_MARK_DURATION, initialMarkDuration);
        }
    }

    @Override
    public GCType type() {
        return GCType.CMS;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(CMSPhase.CMS_INITIAL_MARK.name(), CMSPhase.CMS_INITIAL_MARK.isSTW()));
    }
}
