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
public class CMSFinalRemarkPhase implements Phase {

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
            CMS_OLD_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            CMS_OLD_SIZE +
            ">\\d+\\w)\\)\\]\\s(?<" +
            HEAP_USAGE_AFTER +
            ">\\d+\\w)\\((?<" +
            HEAP_SIZE +
            ">\\d+\\w)\\),\\s(?<" +
            CMS_FINAL_REMARK_DURATION +
            ">\\d+\\.\\d+)";

    public static final Pattern FINAL_REMARK__PATTERN = Pattern.compile(FINAL_REMARK_PHASE);

    @Override
    public void action(String message, GCData data) {
        if(!message.contains(CMS_FINAL_REMARK)){
            return;
        }
        Matcher matcher = FINAL_REMARK__PATTERN.matcher(message);
        if (!matcher.find()) {
            return;
        }
        String youngUsage;
        if(StringUtils.isNotEmpty(youngUsage = matcher.group(YOUNG_USAGE))){
            data.addProperties(YOUNG_USAGE, youngUsage);
        }
        String youngSize;
        if(StringUtils.isNotEmpty(youngSize = matcher.group(YOUNG_SIZE))){
            data.addProperties(YOUNG_SIZE, youngSize);
        }
        String rescanDuration;
        if(StringUtils.isNotEmpty(rescanDuration = matcher.group(CMS_RESCAN_DURATION))){
            data.addProperties(CMS_RESCAN_DURATION, rescanDuration);
        }
        String weakRefsProcessingDuration;
        if(StringUtils.isNotEmpty(weakRefsProcessingDuration = matcher.group(CMS_WEAK_REFS_PROCESSING_DURATION))){
            data.addProperties(CMS_WEAK_REFS_PROCESSING_DURATION, weakRefsProcessingDuration);
        }
        String oldUsageAfter;
        if(StringUtils.isNotEmpty(oldUsageAfter = matcher.group(CMS_OLD_USAGE_AFTER))){
            data.addProperties(CMS_OLD_USAGE_AFTER, oldUsageAfter);
        }
        String oldSize;
        if(StringUtils.isNotEmpty(oldSize = matcher.group(CMS_OLD_SIZE))){
            data.addProperties(CMS_OLD_SIZE, oldSize);
        }
        String heapUsageAfter;
        if(StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))){
            data.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
        }
        String heapSize;
        if(StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))){
            data.addProperties(HEAP_SIZE, heapSize);
        }
        String finalRemarkDuration;
        if(StringUtils.isNotEmpty(finalRemarkDuration = matcher.group(CMS_FINAL_REMARK_DURATION))){
            data.addProperties(CMS_FINAL_REMARK_DURATION, finalRemarkDuration);
        }
        //
        data.setType(GCType.CMS);
        data.setPhase(name());
    }

    @Override
    public GCPhase name() {
        return GCPhase.CMS_FINAL_REMARK;
    }

}
