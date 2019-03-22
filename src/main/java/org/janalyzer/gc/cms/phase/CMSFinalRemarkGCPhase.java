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
public class CMSFinalRemarkGCPhase extends CommonGCAction {

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
    public boolean match(String message) {
        if(!message.contains(CMS_FINAL_REMARK)){
            return false;
        }
        Matcher matcher = FINAL_REMARK__PATTERN.matcher(message);

        return matcher.find();
    }

    @Override
    public void doAction(String message, GCData gcData) {
        //
        Matcher matcher = FINAL_REMARK__PATTERN.matcher(message);
        matcher.find();
        //
        String youngUsage;
        if(StringUtils.isNotEmpty(youngUsage = matcher.group(YOUNG_USAGE))){
            gcData.addProperties(YOUNG_USAGE, youngUsage);
        }
        String youngSize;
        if(StringUtils.isNotEmpty(youngSize = matcher.group(YOUNG_SIZE))){
            gcData.addProperties(YOUNG_SIZE, youngSize);
        }
        String rescanDuration;
        if(StringUtils.isNotEmpty(rescanDuration = matcher.group(CMS_RESCAN_DURATION))){
            gcData.addProperties(CMS_RESCAN_DURATION, rescanDuration);
        }
        String weakRefsProcessingDuration;
        if(StringUtils.isNotEmpty(weakRefsProcessingDuration = matcher.group(CMS_WEAK_REFS_PROCESSING_DURATION))){
            gcData.addProperties(CMS_WEAK_REFS_PROCESSING_DURATION, weakRefsProcessingDuration);
        }
        String oldUsageAfter;
        if(StringUtils.isNotEmpty(oldUsageAfter = matcher.group(OLD_USAGE_AFTER))){
            gcData.addProperties(OLD_USAGE_AFTER, oldUsageAfter);
        }
        String oldSize;
        if(StringUtils.isNotEmpty(oldSize = matcher.group(OLD_SIZE))){
            gcData.addProperties(OLD_SIZE, oldSize);
        }
        String heapUsageAfter;
        if(StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))){
            gcData.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
        }
        String heapSize;
        if(StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))){
            gcData.addProperties(HEAP_SIZE, heapSize);
        }
        String finalRemarkDuration;
        if(StringUtils.isNotEmpty(finalRemarkDuration = matcher.group(CMS_FINAL_REMARK_DURATION))){
            gcData.addProperties(CMS_FINAL_REMARK_DURATION, finalRemarkDuration);
        }
    }

    @Override
    public GCType type() {
        return GCType.CMS;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.of(new GCPhase(CMSPhase.CMS_FINAL_REMARK.name(), CMSPhase.CMS_FINAL_REMARK.isSTW()));
    }
}
