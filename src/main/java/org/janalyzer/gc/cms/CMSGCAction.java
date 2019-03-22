package org.janalyzer.gc.cms;

import org.janalyzer.gc.*;
import org.janalyzer.gc.cms.phase.CMSPhase;
import org.janalyzer.util.Optional;
import org.janalyzer.util.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.janalyzer.util.Constants.*;


/**
 * @Author: Tboy
 */
public class CMSGCAction implements GCAction<Optional<List<GCData>>> {

    private final CMSFullGCAction fullGCAction = new CMSFullGCAction();

    private final PhaseChain phaseChain = new CMSPhaseChain();

    public Optional<List<GCData>> action(String message){
        if(StringUtils.isEmpty(message)){
            throw new IllegalArgumentException("message is empty");
        }

        Optional<GCData> fullAction = fullGCAction.action(message);
        if(fullAction.isPresent()){
            return Optional.of(Arrays.asList(fullAction.get()));
        } else{
            return phaseChain.doPhase(message);
        }
    }

    @Override
    public GCType type() {
        return GCType.CMS;
    }

    @Override
    public Optional<GCPhase> phase() {
        return Optional.empty();
    }

    static class CMSFullGCAction extends CommonGCAction {

        public static final String CMS_FULL_GC_ACTION =
                ".*" + FULL_GC + "\\s\\((?<" +
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
        public boolean match(String message) {
            if(!message.contains(FULL_GC) || !message.contains(CMS_FULL_GC)){
                return false;
            }
            Matcher matcher = CMS_FULL_GC_PATTERN.matcher(message);

            return matcher.find();
        }

        @Override
        public void doAction(String message, GCData gcData) {
            //
            Matcher matcher = CMS_FULL_GC_PATTERN.matcher(message);
            matcher.find();
            //
            String caution;
            if (StringUtils.isNotEmpty(caution = matcher.group(CMS_FULL_GC_CAUTION))) {
                gcData.addProperties(CMS_FULL_GC_CAUTION, caution);
            }
            String oldUsageBefore;
            if (StringUtils.isNotEmpty(oldUsageBefore = matcher.group(OLD_USAGE_BEFORE))) {
                gcData.addProperties(OLD_USAGE_BEFORE, oldUsageBefore);
            }
            String oldUsageAfter;
            if (StringUtils.isNotEmpty(oldUsageAfter = matcher.group(OLD_USAGE_AFTER))) {
                gcData.addProperties(OLD_USAGE_AFTER, oldUsageAfter);
            }
            String oldSize;
            if (StringUtils.isNotEmpty(oldSize = matcher.group(OLD_SIZE))) {
                gcData.addProperties(OLD_SIZE, oldSize);
            }
            String duration;
            if (StringUtils.isNotEmpty(duration = matcher.group(CMS_FULL_GC_DURATION))) {
                gcData.addProperties(CMS_FULL_GC_DURATION, duration);
            }
            String heapUsageBefore;
            if (StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))) {
                gcData.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
            }
            String heapUsageAfter;
            if (StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))) {
                gcData.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
            }
            String heapSize;
            if (StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))) {
                gcData.addProperties(HEAP_SIZE, heapSize);
            }
        }

        @Override
        public GCType type() {
            return GCType.CMS;
        }

        @Override
        public Optional<GCPhase> phase() {
            return Optional.of(new GCPhase(CMSPhase.CMS_FULL_GC.name(), CMSPhase.CMS_FULL_GC.isSTW()));
        }
    }
}
