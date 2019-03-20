package org.janalyzer.gc.g1.phase;

/**
 * @Author: Tboy
 */
public enum G1Phase {

    G1_INITIAL_MARK("它标记了从GC Root开始直接可达的对象, STW", true),

    G1_ROOT_REGION_SCAN("标记所有GC Root可达的对象", false),

    G1_CONCURRENT_MARK("这个阶段从GC Root开始对heap中的对象标记，标记线程与应用程序线程并行执行，并且收集各个Region的存活对象信息", false),

    G1_REMARK("标记那些在并发标记阶段发生变化的对象，将被回收, STW",true),

    G1_CLEANUP("清除空Region（没有存活对象的），加入到free list", false);

    private String shortDescription;

    private boolean isSTW;

    G1Phase(String shortDescription, boolean isSTW){
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
