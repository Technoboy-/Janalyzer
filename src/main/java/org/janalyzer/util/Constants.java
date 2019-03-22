package org.janalyzer.util;

/**
 * @Author: Tboy
 */
public class Constants {

    //common
    public static final String DATETIME = "datetime";

    public static final String TIMES = "Times";

    public static final String USER_TIME = "UserTime";

    public static final String SYS_TIME = "SysTime";

    public static final String REAL_TIME = "RealTime";

    //parnew
    public static final String PARNEW_CAUTION = "ParNewCaution";

    public static final String PARNEW = "ParNew";

    public static final String PARNEW_CLEANUP_DURATION = "ParNewCleanupDuration";

    public static final String PARNEW_DURATION = "ParNewDuration";
    //CMS init-mark
    public static final String CMS_INITIAL_MARK = "CMS-initial-mark";

    public static final String CMS_INITIAL_MARK_DURATION = "CMSInitialMarkDuration";

    //CMS concurrent mark
    public static final String CMS_CONCURRENT_MARK = "CMS-concurrent-mark";

    public static final String CMS_CONCURRENT_MARK_DURATION = "CMSConcurrentMarkDuration";

    //CMS concurrent preclean
    public static final String CMS_CONCURRENT_PRECLEAN = "CMS-concurrent-preclean";

    public static final String CMS_CONCURRENT_PRECLEAN_DURATION = "CMSConcurrentPrecleanDuration";

    //CMS concurrent abortable preclean
    public static final String CMS_CONCURRENT_ABORTABLE_PRECLEAN = "CMS-concurrent-abortable-preclean";

    public static final String CMS_CONCURRENT_ABORTABLE_PRECLEAN_DURATION = "CMSConcurrentAbortablePrecleanDuration";

    //CMS final remark
    public static final String CMS_FINAL_REMARK = "CMS Final Remark";

    public static final String CMS_WEAK_REFS_PROCESSING_DURATION = "CMSWeakRefsProcessingDuration";

    public static final String CMS_RESCAN_DURATION = "CMSRescanDuration";

    public static final String CMS_FINAL_REMARK_DURATION = "CMSFinalRemarkDuration";

    //CMS concurrent sweep
    public static final String CMS_CONCURRENT_SWEEP = "CMS-concurrent-sweep";

    public static final String CMS_CONCURRENT_SWEEP_DURATION = "CMSConcurrentSweepDuration";

    //CMS concurrent reset
    public static final String CMS_CONCURRENT_RESET = "CMS-concurrent-reset";

    public static final String CMS_CONCURRENT_RESET_DURATION = "CMSConcurrentResetDuration";

    //CMS full GC
    public static final String CMS_FULL_GC = "CMS";

    public static final String CMS_FULL_GC_CAUTION = "CMSFullGCCaution";

    public static final String CMS_FULL_GC_DURATION = "CMSFullGCDuration";

    //parallel scavenge
    public static final String PARALLEL_SCAVENGE = "PSYoungGen";

    public static final String PARALLEL_SCAVENGE_CAUTION = "ParallelScavengeCaution";

    public static final String PARALLEL_SCAVENGE_DURATION = "ParallelScavengeDuration";

    //parallel old
    public static final String PARALLEL_OLD = "ParOldGen";

    public static final String PARALLEL_OLD_CAUTION = "ParallelOldCaution";

    public static final String PARALLEL_OLD_DURATION = "ParallelOldDuration";

    //serial
    public static final String SERIAL = "DefNew";

    public static final String SERIAL_CAUTION = "SerialCaution";

    public static final String SERIAL_YOUNG_DURATION = "SerialYoungDuration";

    public static final String SERIAL_DURATION = "SerialDuration";

    //serial old
    public static final String SERIAL_OLD = "Tenured";

    public static final String SERIAL_OLD_CAUTION = "SerialOldCaution";

    public static final String SERIAL_OLD_CLEANUP_OLD_DURATION = "SerialOLDCleanupOldDuration";

    public static final String SERIAL_OLD_DURATION = "SerialOLDDuration";

    //G1 initial mark
    public static final String G1 = "G1";

    public static final String G1_CAUTION = "G1Caution";

    public static final String G1_WORKER_COUNT = "G1WorkerCount";

    public static final String G1_EXT_ROOT_SCANNING_DURATION = "G1ExtRootScanningDuration";

    public static final String G1_CODE_ROOT_MARKING_DURATION = "G1CodeRootMarkingDuration";

    public static final String G1_UPDATE_RS_DURATION = "G1UpdateRSDuration";

    public static final String G1_PROCESSED_BUFFERS_DURATION = "G1ProcessedBuffersDuration";

    public static final String G1_SCAN_RS_DURATION = "G1ScanRSDuration";

    public static final String G1_CODE_ROOT_SCAN_DURATION = "G1CodeRootScanDuration";

    public static final String G1_OBJECT_COPY_DURATION = "G1ObjectCopyDuration";

    public static final String G1_TERMINATION_DURATION = "G1TTerminationDuration";

    public static final String G1_CODE_ROOT_FIXUP = "G1CodeRootFixup";

    public static final String G1_CODE_ROOT_PURGE_OR_MIGRATION_DURATION = "G1CodeRootPurgeOrMigrationDuration";

    public static final String G1_OTHER_DURATION = "G1OtherDuration";

    public static final String G1_CLEAR_CT_DURATION = "G1ClearCtDuration";

    public static final String G1_CHOOSE_CSET_DURATION = "G1ChooseCSetDuration";

    public static final String G1_REF_PROC_DURATION = "G1RefProcDuration";

    public static final String G1_REF_ENQ_DURATION = "G1RefEnqDuration";

    public static final String G1_REDIRTY_CARDS_DURATION = "G1RedirtyCardsDuration";

    public static final String G1_HUMONGOUS_REGISTER_DURATION = "G1HumongousRegister";

    public static final String G1_HUMONGOUS_RECLAIM_DURATION = "G1HumongousReclaimDuration";

    public static final String G1_FREE_CSET_DURATION = "G1FreeCSetDuration";

    //G1 root region scan
    public static final String G1_ROOT_REGION_SCAN = "concurrent-root-region-scan-end";

    public static final String G1_ROOT_REGION_SCAN_DURATION = "G1RootRegionScanDuration";

    //G1 concurrent mark
    public static final String G1_CONCURRENT_MARK = "concurrent-mark-end";

    public static final String G1_CONCURRENT_MARK_DURATION = "G1ConcurrentMarkDuration";

    //G1 remark
    public static final String G1_REMARK = "remark";

    public static final String G1_FINALIZE_MARKING_DURATION = "G1FinalizeMarkingDuration";

    public static final String G1_GC_REF_PROC_DURATION = "G1GCRefProcDuration";

    public static final String G1_UNLOADING_DURATION = "G1UnloadingDuration";

    public static final String G1_REMARK_DURATION = "G1RemarkDuration";

    //G1 cleanup
    public static final String G1_CLEANUP = "cleanup";

    public static final String G1_CLEANUP_DURATION = "G1CleanupDuration";

    //G1 full GC
    public static final String G1_FULL_GC_CAUTION = "G1FullGCCaution";

    public static final String G1_FULL_GC_DURATION = "G1FullGCDuration";

    //young
    public static final String YOUNG_USAGE = "YoungUsage";

    public static final String YOUNG_SIZE = "YoungSize";

    public static final String YOUNG_USAGE_BEFORE = "YoungUsageBefore";

    public static final String YOUNG_USAGE_AFTER = "YoungUsageAfter";

    //eden
    public static final String EDEN_USAGE = "EdenUsage";

    public static final String EDEN_USAGE_BEFORE = "EdenUsageBefore";

    public static final String EDEN_USAGE_AFTER = "EdenUsageAfter";

    public static final String EDEN_SIZE = "EdenSize";

    //survivor
    public static final String SURVIVOR_BEFORE = "SurvivorBefore";

    public static final String SURVIVOR_AFTER = "SurvivorAfter";

    //old
    public static final String OLD_USAGE_BEFORE = "OldUsageBefore";

    public static final String OLD_USAGE_AFTER = "OldUsageAfter";

    public static final String OLD_SIZE = "OldSize";

    //heap
    public static final String HEAP_USAGE_BEFORE = "HeapUsageBefore";

    public static final String HEAP_USAGE_AFTER = "HeapUsageAfter";

    public static final String HEAP_SIZE = "HeapSize";

    //full gc
    public static final String FULL_GC = "Full GC";
}
