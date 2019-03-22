package org.janalyzer.gc.cms.phase;

/**
 * @Author: Tboy
 */
public enum CMSPhase {

    CMS_FULL_GC("full GC", true),

    CMS_INITIAL_MARK("它有两个目标：一是标记老年代中所有的GC Roots；二是标记年轻代中活着的对象引用的对象。STW", true),

    CMS_CONCURRENT_MARK("这个阶段会遍历整个老年代并且标记所有存活的对象，从“初始化标记”阶段找到的GC Roots开始。并发标记的特点是和应用程序线程同时运行。并不是老年代的所有存活对象都会被标记，因为标记的同时应用程序会改变一些对象的引用等", false),

    CMS_CONCURRENT_PRECLEAN("这个阶段又是一个并发阶段，和应用线程并行运行，不会中断他们。前一个阶段在并行运行的时候，一些对象的引用已经发生了变化，当这些引用发生变化的时候，JVM会标记堆的这个区域为Dirty Card(包含被标记但是改变了的对象，被认为dirty，这就是 Card Marking", false),

    CMS_CONCURRENT_ABORTABLE_PRECLEAN("又一个并发阶段不会停止应用程序线程。这个阶段尝试着去承担STW的Final Remark阶段足够多的工作。这个阶段持续的时间依赖好多的因素，由于这个阶段是重复的做相同的事情直到发生aboart的条件（比如：重复的次数、多少量的工作、持续的时间等等）之一才会停止", false),

    CMS_FINAL_REMARK("该阶段的任务是完成标记整个年老代的所有的存活对象。由于之前的预处理是并发的，它可能跟不上应用程序改变的速度，这个时候，STW是非常需要的来完成这个严酷考验的阶段。\n" +
            "通常CMS尽量运行Final Remark阶段在年轻代是足够干净的时候，目的是消除紧接着的连续的几个STW阶段", true),

    CMS_CONCURRENT_SWEEP("移除那些不用的对象，回收他们占用的空间并且为将来使用", false),

    CMS_CONCURRENT_RESET("重新设置CMS算法内部的数据结构，准备下一个CMS生命周期的使用", false);

    private String shortDescription;

    private boolean isSTW;

    CMSPhase(String shortDescription, boolean isSTW){
        this.shortDescription = shortDescription;
        this.isSTW = isSTW;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


    public boolean isSTW() {
        return isSTW;
    }

    public void setSTW(boolean STW) {
        isSTW = STW;
    }
}
