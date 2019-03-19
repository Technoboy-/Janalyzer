package org.janaylzer.gc.cms.phase;

/**
 * @Author: Tboy
 */
public enum CMSPhase {

    CMS_INITIAL_MARK,

    CMS_CONCURRENT_MARK,

    CMS_CONCURRENT_PRECLEAN,

    CMS_CONCURRENT_ABORTABLE_PRECLEAN,

    CMS_CONCURRENT_SWEEP,

    CMS_CONCURRENT_RESET,

    CMS_FINAL_REMARK;

}
