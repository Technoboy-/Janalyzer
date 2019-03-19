package org.janaylzer.gc.cms;

import org.janaylzer.gc.GCAction;
import org.janaylzer.gc.GCData;
import org.janaylzer.gc.GCPhase;
import org.janaylzer.gc.GCType;
import org.janaylzer.gc.cms.phase.PhaseChain;
import org.janaylzer.util.Optional;
import org.janaylzer.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janaylzer.util.Constants.*;


/**
 * @Author: Tboy
 */
public class CMSGCAction implements GCAction<Optional<GCData>> {

    private final CMSFullGCAction fullGCAction = new CMSFullGCAction();

    private final PhaseChain phaseChain = new CMSPhaseChain();

    public Optional<GCData> action(String message){
        if(StringUtils.isEmpty(message)){
            throw new IllegalArgumentException("message is empty");
        }

        Optional<GCData> fullAction = fullGCAction.action(message);
        if(fullAction.isPresent()){
            return fullAction;
        } else{
            List<GCPhase> gcPhases = phaseChain.doPhase(message);
            GCData data = new GCData(GCType.CMS);
            data.setPhases(gcPhases);
            return Optional.of(data);
        }
    }

    static class CMSFullGCAction implements GCAction<Optional<GCData>>{

        public static final String CMS_FULL_GC_ACTION =
                ".*Full GC\\s\\((?<" +
                CMS_FULL_GC_CAUTION +
                ">.*)\\).*\\[" +
                CMS_FULL_GC +
                ":\\s(?<" +
                OLD_USAGE_BEFORE +
                ">\\d+\\w)->(?<" +
                OLD_USAGE_AFTER +
                ">\\d+\\w)\\((?<" +
                OLD_SIZE +
                ">\\d+\\w)\\),\\s(?<" +
                CMS_FULL_GC_DURATION +
                ">\\d+\\.\\d+)\\ssecs]\\s(?<" +
                HEAP_USAGE_BEFORE +
                ">\\d+\\w)->(?<" +
                HEAP_USAGE_AFTER +
                ">\\d+\\w)\\((?<" +
                HEAP_SIZE +
                ">\\d+\\w)\\)";

        private static final Pattern CMS_FULL_GC_PATTERN = Pattern.compile(CMS_FULL_GC_ACTION);

        @Override
        public Optional<GCData> action(String message) {
            if(!message.contains(CMS_FULL_GC)){
                return Optional.empty();
            }
            Matcher matcher = CMS_FULL_GC_PATTERN.matcher(message);
            if (!matcher.find()) {
                return Optional.empty();
            }
            GCData data = new GCData();
            String caution;
            if (StringUtils.isNotEmpty(caution = matcher.group(CMS_FULL_GC_CAUTION))) {
                data.addProperties(CMS_FULL_GC_CAUTION, caution);
            }
            String oldUsageBefore;
            if (StringUtils.isNotEmpty(oldUsageBefore = matcher.group(OLD_USAGE_BEFORE))) {
                data.addProperties(OLD_USAGE_BEFORE, oldUsageBefore);
            }
            String oldUsageAfter;
            if (StringUtils.isNotEmpty(oldUsageAfter = matcher.group(OLD_USAGE_AFTER))) {
                data.addProperties(OLD_USAGE_AFTER, oldUsageAfter);
            }
            String oldSize;
            if (StringUtils.isNotEmpty(oldSize = matcher.group(OLD_SIZE))) {
                data.addProperties(OLD_SIZE, oldSize);
            }
            String duration;
            if (StringUtils.isNotEmpty(duration = matcher.group(CMS_FULL_GC_DURATION))) {
                data.addProperties(CMS_FULL_GC_DURATION, duration);
            }
            String heapUsageBefore;
            if (StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))) {
                data.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
            }
            String heapUsageAfter;
            if (StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))) {
                data.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
            }
            String heapSize;
            if (StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))) {
                data.addProperties(HEAP_SIZE, heapSize);
            }

            //
            data.setType(GCType.CMS);

            return Optional.of(data);
        }
    }
}
