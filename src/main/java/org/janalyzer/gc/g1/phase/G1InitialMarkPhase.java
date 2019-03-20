package org.janalyzer.gc.g1.phase;

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
public class G1InitialMarkPhase implements Phase<Optional<GCPhase>> {

    private static final String COMMON = ".*\\[GC pause\\s\\((?<" + G1_CAUTION + ">.*)\\)" + "\\s\\((young|mixed)\\).*,\\s\\d+.\\d+\\ssecs\\]";

    private static final String DETAIL =
            "\\s*\\[Parallel Time:\\s\\d+.\\d+\\sms,\\sGC Workers:\\s(?<" + G1_WORKER_COUNT +">\\d+)\\]\\s*" +
            "\\[GC Worker Start\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*\\]\\s*" +
            "\\[Ext Root Scanning.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s(?<" + G1_EXT_ROOT_SCANNING_DURATION + ">\\d+.\\d+)\\]\\s*" +
            "(\\[Code Root Marking\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s(?<" + G1_CODE_ROOT_MARKING_DURATION + ">\\d+.\\d+)\\]\\s*)?" +
            "\\[Update RS\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s(?<" + G1_UPDATE_RS_DURATION + ">\\d+.\\d+)\\]\\s*" +
            "\\[Processed Buffers:\\sMin:\\s.*,\\sAvg:.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s(?<" + G1_PROCESSED_BUFFERS_DURATION + ">(\\d+|\\d+.\\d+))\\]\\s*" +
            "\\[Scan RS\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s(?<" + G1_SCAN_RS_DURATION + ">\\d+.\\d+)\\]\\s*" +
            "\\[Code Root Scanning\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s(?<" + G1_CODE_ROOT_SCAN_DURATION + ">\\d+.\\d+)\\]\\s*" +
            "\\[Object Copy\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s(?<" + G1_OBJECT_COPY_DURATION + ">\\d+.\\d+)\\]\\s*" +
            "\\[Termination\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s(?<" + G1_TERMINATION_DURATION + ">\\d+.\\d+)\\]\\s*" +
            "(\\[Termination Attempts:\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s.*\\]\\s*)?" +
            "\\[GC Worker Other\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s.*,\\sDiff:\\s.*,\\sSum:\\s.*\\]\\s*" +
            "\\[GC Worker Total\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s" + ".*,\\sDiff:\\s.*,\\sSum:\\s.*\\]\\s*" +
            "\\[GC Worker End\\s\\(ms\\):\\sMin:\\s.*,\\sAvg:\\s.*,\\sMax:\\s" + ".*,\\sDiff:\\s.*\\]\\s*" +
            "\\[Code Root Fixup:\\s(?<" + G1_CODE_ROOT_FIXUP + ">\\d+.\\d+)\\sms\\]\\s*" +
            "\\[Code Root (Purge|Migration):\\s(?<" + G1_CODE_ROOT_PURGE_OR_MIGRATION_DURATION + ">\\d+.\\d+)\\sms\\]\\s*" +
            "\\[Clear CT:\\s(?<" + G1_CLEAR_CT_DURATION + ">\\d+.\\d+)\\sms\\]\\s*";

    public static final String OTHER_DETAIL =
            "\\[Other:\\s(?<" + G1_OTHER_DURATION + ">\\d+.\\d+)\\sms\\]\\s*" +
            "\\[Choose CSet:\\s(?<" + G1_CHOOSE_CSET_DURATION + ">\\d+\\.\\d+)\\sms\\]\\s*" +
            "\\[Ref Proc:\\s(?<" + G1_REF_PROC_DURATION + ">\\d+.\\d+)\\sms\\]\\s*" +
            "\\[Ref Enq:\\s(?<" + G1_REF_ENQ_DURATION + ">\\d+.\\d+)\\sms\\]\\s*" +
            "(\\[Redirty Cards:\\s(?<" + G1_REDIRTY_CARDS_DURATION + ">\\d+.\\d+)\\sms\\]\\s*" +
            "\\[Humongous Register:\\s(?<" + G1_HUMONGOUS_REGISTER_DURATION + ">\\d+.\\d+)\\sms\\]\\s*" +
            "\\[Humongous Reclaim:\\s(?<" + G1_HUMONGOUS_RECLAIM_DURATION + ">\\d+.\\d+)\\sms\\]\\s*)?" +
            "\\[Free CSet:\\s(?<" + G1_FREE_CSET_DURATION + ">\\d+.\\d+)\\sms\\]\\s*" +
            "\\[Eden:(?<" + EDEN_USAGE_BEFORE + ">\\s\\d+.\\d+\\w)\\((?<" + EDEN_USAGE + ">.*)\\)->(?<" + EDEN_USAGE_AFTER + ">\\d+.\\d+\\w)\\(.*\\)\\s" +
            "Survivors:\\s(?<" + SURVIVOR_BEFORE + ">\\d+.\\d+\\w)->(?<" + SURVIVOR_AFTER + ">\\d+.\\d+\\w)\\s" +
            "Heap:\\s(?<" + HEAP_USAGE_BEFORE + ">\\d+\\.\\d+\\w)\\(.*\\)->(?<" + HEAP_USAGE_AFTER + ">\\d+\\.\\d+\\w)\\((?<" + HEAP_SIZE + ">\\d+.\\d+\\w)\\)\\]\\s*";

    private static final Pattern INITIAL_MARK_PATTERN = Pattern.compile(COMMON + DETAIL + OTHER_DETAIL);

    @Override
    public Optional<GCPhase> action(String message) {
        if(!message.contains(G1)){
            return Optional.empty();
        }
        Matcher matcher = INITIAL_MARK_PATTERN.matcher(message);
        if (!matcher.find()) {
            return Optional.empty();
        }

        GCPhase phase = new GCPhase(name());

        String caution;
        if (StringUtils.isNotEmpty(caution = matcher.group(G1_CAUTION))) {
            phase.addProperties(G1_CAUTION, caution);
        }
        String workerCount;
        if (StringUtils.isNotEmpty(workerCount = matcher.group(G1_WORKER_COUNT))) {
            phase.addProperties(G1_WORKER_COUNT, workerCount);
        }
        String extRootScanningDuration;
        if (StringUtils.isNotEmpty(extRootScanningDuration = matcher.group(G1_EXT_ROOT_SCANNING_DURATION))) {
            phase.addProperties(G1_EXT_ROOT_SCANNING_DURATION, extRootScanningDuration);
        }
        String codeRootMarkingDuration;
        if (StringUtils.isNotEmpty(codeRootMarkingDuration = matcher.group(G1_CODE_ROOT_MARKING_DURATION))) {
            phase.addProperties(G1_CODE_ROOT_MARKING_DURATION, codeRootMarkingDuration);
        }
        String updateRSDuration;
        if (StringUtils.isNotEmpty(updateRSDuration = matcher.group(G1_UPDATE_RS_DURATION))) {
            phase.addProperties(G1_UPDATE_RS_DURATION, updateRSDuration);
        }
        String processedBuffersDuration;
        if (StringUtils.isNotEmpty(processedBuffersDuration = matcher.group(G1_PROCESSED_BUFFERS_DURATION))) {
            phase.addProperties(G1_PROCESSED_BUFFERS_DURATION, processedBuffersDuration);
        }
        String scanRSDuration;
        if (StringUtils.isNotEmpty(scanRSDuration = matcher.group(G1_SCAN_RS_DURATION))) {
            phase.addProperties(G1_SCAN_RS_DURATION, scanRSDuration);
        }
        String codeRootScanDuration;
        if (StringUtils.isNotEmpty(codeRootScanDuration = matcher.group(G1_CODE_ROOT_SCAN_DURATION))) {
            phase.addProperties(G1_CODE_ROOT_SCAN_DURATION, codeRootScanDuration);
        }
        String objectCopyDuration;
        if (StringUtils.isNotEmpty(objectCopyDuration = matcher.group(G1_OBJECT_COPY_DURATION))) {
            phase.addProperties(G1_OBJECT_COPY_DURATION, objectCopyDuration);
        }
        String terminationDuration;
        if (StringUtils.isNotEmpty(terminationDuration = matcher.group(G1_TERMINATION_DURATION))) {
            phase.addProperties(G1_TERMINATION_DURATION, terminationDuration);
        }
        String codeRootFixupDuration;
        if (StringUtils.isNotEmpty(codeRootFixupDuration = matcher.group(G1_CODE_ROOT_FIXUP))) {
            phase.addProperties(G1_CODE_ROOT_FIXUP, codeRootFixupDuration);
        }
        String codeRootPurgeOrMigrationDuration;
        if (StringUtils.isNotEmpty(codeRootPurgeOrMigrationDuration = matcher.group(G1_CODE_ROOT_PURGE_OR_MIGRATION_DURATION))) {
            phase.addProperties(G1_CODE_ROOT_PURGE_OR_MIGRATION_DURATION, codeRootPurgeOrMigrationDuration);
        }
        String clearCTDuration;
        if (StringUtils.isNotEmpty(clearCTDuration = matcher.group(G1_CLEAR_CT_DURATION))) {
            phase.addProperties(G1_CLEAR_CT_DURATION, clearCTDuration);
        }
        //other
        String otherDuration;
        if (StringUtils.isNotEmpty(otherDuration = matcher.group(G1_OTHER_DURATION))) {
            phase.addProperties(G1_OTHER_DURATION, otherDuration);
        }
        String chooseCSetDuration;
        if (StringUtils.isNotEmpty(chooseCSetDuration = matcher.group(G1_CHOOSE_CSET_DURATION))) {
            phase.addProperties(G1_CHOOSE_CSET_DURATION, chooseCSetDuration);
        }
        String refProcDuration;
        if (StringUtils.isNotEmpty(refProcDuration = matcher.group(G1_REF_PROC_DURATION))) {
            phase.addProperties(G1_REF_PROC_DURATION, refProcDuration);
        }
        String refEnqDuration;
        if (StringUtils.isNotEmpty(refEnqDuration = matcher.group(G1_REF_ENQ_DURATION))) {
            phase.addProperties(G1_REF_ENQ_DURATION, refEnqDuration);
        }
        String redirtyCardsDuration;
        if (StringUtils.isNotEmpty(redirtyCardsDuration = matcher.group(G1_REDIRTY_CARDS_DURATION))) {
            phase.addProperties(G1_REDIRTY_CARDS_DURATION, redirtyCardsDuration);
        }
        String humongousRegisterDuration;
        if (StringUtils.isNotEmpty(humongousRegisterDuration = matcher.group(G1_HUMONGOUS_REGISTER_DURATION))) {
            phase.addProperties(G1_HUMONGOUS_REGISTER_DURATION, humongousRegisterDuration);
        }
        String humongousReclaimDuration;
        if (StringUtils.isNotEmpty(humongousReclaimDuration = matcher.group(G1_HUMONGOUS_RECLAIM_DURATION))) {
            phase.addProperties(G1_HUMONGOUS_RECLAIM_DURATION, humongousReclaimDuration);
        }
        String freeCSetDuration;
        if (StringUtils.isNotEmpty(freeCSetDuration = matcher.group(G1_FREE_CSET_DURATION))) {
            phase.addProperties(G1_FREE_CSET_DURATION, freeCSetDuration);
        }
        //eden
        String edenUsageBefore;
        if (StringUtils.isNotEmpty(edenUsageBefore = matcher.group(EDEN_USAGE_BEFORE))) {
            phase.addProperties(EDEN_USAGE_BEFORE, edenUsageBefore);
        }
        String edenUsage;
        if (StringUtils.isNotEmpty(edenUsage = matcher.group(EDEN_USAGE))) {
            phase.addProperties(EDEN_USAGE, edenUsage);
        }
        String edenUsageAfter;
        if (StringUtils.isNotEmpty(edenUsageAfter = matcher.group(EDEN_USAGE_AFTER))) {
            phase.addProperties(EDEN_USAGE_AFTER, edenUsageAfter);
        }
        //survivor
        String survivorBefore;
        if (StringUtils.isNotEmpty(survivorBefore = matcher.group(SURVIVOR_BEFORE))) {
            phase.addProperties(SURVIVOR_BEFORE, survivorBefore);
        }
        String survivorAfter;
        if (StringUtils.isNotEmpty(survivorAfter = matcher.group(SURVIVOR_AFTER))) {
            phase.addProperties(SURVIVOR_AFTER, survivorAfter);
        }
        //heap
        String heapUsageBefore;
        if (StringUtils.isNotEmpty(heapUsageBefore = matcher.group(HEAP_USAGE_BEFORE))) {
            phase.addProperties(HEAP_USAGE_BEFORE, heapUsageBefore);
        }
        String heapUsageAfter;
        if (StringUtils.isNotEmpty(heapUsageAfter = matcher.group(HEAP_USAGE_AFTER))) {
            phase.addProperties(HEAP_USAGE_AFTER, heapUsageAfter);
        }
        String heapSize;
        if (StringUtils.isNotEmpty(heapSize = matcher.group(HEAP_SIZE))) {
            phase.addProperties(HEAP_SIZE, heapSize);
        }
        //
        return Optional.of(phase);
    }

    @Override
    public String name() {
        return G1Phase.G1_INITIAL_MARK.name();
    }
}
