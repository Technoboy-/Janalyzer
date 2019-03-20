package org.janalyzer.gc.cms.phase;

import org.janalyzer.gc.GCPhase;
import org.janalyzer.gc.Phase;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;

/**
 * @Author: Tboy
 */
public class CMSFinalRemarkPhase implements Phase<Optional<GCPhase>> {

    public static final String FINAL_REMARK_PHASE = ".*" +
            CMS_FINAL_REMARK +
            ".*" +
            "YG occupancy:\\s(?<" +
            YOUNG_USAGE +
            ">\\d+\\s).*\\s\\((?<" +
            YOUNG_SIZE +
            ">\\d+\\s.*).*\\)" +
            ".*Rescan\\s.*\\s,\\s(?<" +
            CMS_RESCAN_DURATION +
            ">\\d+\\.\\d+).*weak refs processing((,\\s)|(.*?\\],\\s))(?<" +
            CMS_WEAK_REFS_PROCESSING_DURATION +
            ">\\d+\\.\\d+).*CMS-remark:\\s(?<" +
            OLD_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            OLD_SIZE +
            ">\\d+\\w)\\)\\]\\s(?<" +
            HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            CMS_FINAL_REMARK_DURATION +
            ">\\d+\\.\\d+)";

    public static final Pattern FINAL_REMARK__PATTERN = Pattern.compile(FINAL_REMARK_PHASE);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(CMS_FINAL_REMARK)){
            return Optional.empty();
        }
        Matcher matcher = FINAL_REMARK__PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(name());
        //
        String youngUsage;
        if(StringUtils.isNotEmpty(youngUsage = matcher.group(YOUNG_USAGE))){
            phase.addProperties(YOUNG_USAGE, youngUsage);
        }
        String youngSize;
        if(StringUtils.isNotEmpty(youngSize = matcher.group(YOUNG_SIZE))){
            phase.addProperties(YOUNG_SIZE, youngSize);
        }
        String rescanDuration;
        if(StringUtils.isNotEmpty(rescanDuration = matcher.group(CMS_RESCAN_DURATION))){
            phase.addProperties(CMS_RESCAN_DURATION, rescanDuration);
        }
        String weakRefsProcessingDuration;
        if(StringUtils.isNotEmpty(weakRefsProcessingDuration = matcher.group(CMS_WEAK_REFS_PROCESSING_DURATION))){
            phase.addProperties(CMS_WEAK_REFS_PROCESSING_DURATION, weakRefsProcessingDuration);
        }
        String oldUsageAfter;
        if(StringUtils.isNotEmpty(oldUsageAfter = matcher.group(OLD_USAGE_AFTER))){
            phase.addProperties(OLD_USAGE_AFTER, oldUsageAfter);
        }
        String oldSize;
        if(StringUtils.isNotEmpty(oldSize = matcher.group(OLD_SIZE))){
            phase.addProperties(OLD_SIZE, oldSize);
        }
        String heapUsageAfter;
        if(StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))){
            phase.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
        }
        String heapSize;
        if(StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))){
            phase.addProperties(HEAP_SIZE, heapSize);
        }
        String finalRemarkDuration;
        if(StringUtils.isNotEmpty(finalRemarkDuration = matcher.group(CMS_FINAL_REMARK_DURATION))){
            phase.addProperties(CMS_FINAL_REMARK_DURATION, finalRemarkDuration);
        }
        //
        return Optional.of(phase);
    }

    @Override
    public String name() {
        return CMSPhase.CMS_FINAL_REMARK.name();
    }

}
