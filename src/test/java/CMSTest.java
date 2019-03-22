import org.janalyzer.gc.cms.CMSGCAction;
import org.janalyzer.gc.cms.phase.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Tboy
 */
public class CMSTest {

    /**
     *  通过以下参数可以产生log
     *  -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC -Xms5m -XX:+PrintGCDateStamps
     */
    @Test
    public void generateGCLog(){
        CountDownLatch latch = new CountDownLatch(1);
        AtomicLong counter = new AtomicLong(0);
        Random random = new Random();
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                while(true){
                    list.add("i love coding");
                    if(counter.incrementAndGet() % 1000000 == 0){
                        list = list.subList(random.nextInt(100), list.size());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            //
                        }
                    }
                }
            }
        });
        worker.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void testInitialMark() {
        String message = "2018-08-03T17:44:28.790+0800: 12869719.588: [GC " +
                "(CMS Initial Mark) [1 CMS-initial-mark: 39506K(49152K)] " +
                "784858K(1726912K), 0.0819508 secs] [Times: user=0.00 " +
                "sys=0.00, real=0.08 secs] ";
        CMSInitialMarkGCPhase phase = new CMSInitialMarkGCPhase();
        System.out.println(phase.action(message));
        //
        CMSGCAction action = new CMSGCAction();
        System.out.println(action.action(message));
    }

    @Test
    public void testConcurrentMark() {
        String message = "2019-03-18T15:03:39.501-0800: [CMS-concurrent-mark: 0.041/0.040 secs] [Times: user=0.11 sys=0.01, real=0.04 secs]";
        CMSConcurrentMarkGCPhase phase = new CMSConcurrentMarkGCPhase();
        System.out.println(phase.action(message));
    }

    @Test
    public void testConcurrentPreclean() {
        String message = "2019-03-18T15:03:39.922-0800: [CMS-concurrent-preclean: 0.059/0.080 secs] [Times: user=0.13 sys=0.00, real=0.07 secs]";
        CMSConcurrentPrecleanGCPhase phase = new CMSConcurrentPrecleanGCPhase();
        System.out.println(phase.action(message));
    }


    @Test
    public void testConcurrentAbortablePreclean() {
        String message = "2019-03-18T15:03:39.501-0800: [CMS-concurrent-abortable-preclean: 0.167/1.074 secs] [Times: user=0.20 sys=0.00, real=1.07 secs]";
        CMSConcurrentAbortablePrecleanPhase phase = new CMSConcurrentAbortablePrecleanPhase();
        System.out.println(phase.action(message));
    }

    @Test
    public void testFinalRemark() {
        String message = "2019-03-18T15:03:40.411-0800: [GC (CMS Final Remark) [YG occupancy: 0 K (68608 K)]2019-03-18T15:03:40.411-0800: [Rescan (parallel) , 0.0002597 secs]2019-03-18T15:03:40.411-0800: [weak refs processing, 0.0000120 secs]2019-03-18T15:03:40.411-0800: [class unloading, 0.0005750 secs]2019-03-18T15:03:40.412-0800: [scrub symbol table, 0.0004445 secs]2019-03-18T15:03:40.412-0800: [scrub string table, 0.0001678 secs][1 CMS-remark: 136519K(152420K)] 136520K(221028K), 0.0015073 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] ";
        CMSFinalRemarkGCPhase phase = new CMSFinalRemarkGCPhase();
        System.out.println(phase.action(message));
    }

    @Test
    public void testConcurrentSweep() {
        String message = "2019-03-18T15:03:39.548-0800: [CMS-concurrent-sweep: 0.002/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]";
        CMSConcurrentSweepGCPhase phase = new CMSConcurrentSweepGCPhase();
        System.out.println(phase.action(message));
    }

    @Test
    public void testConcurrentReset() {
        String message = "2019-03-18T15:03:39.549-0800: [CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] ";
        CMSConcurrentResetGCPhase phase = new CMSConcurrentResetGCPhase();
        System.out.println(phase.action(message));
    }

    @Test
    public void testFullGC() {
        String message = "2019-03-19T16:12:52.987-0800: [Full GC (Allocation Failure) 2019-03-19T16:12:52.987-0800: [CMS: 616757K->616695K(1415616K), 0.7754829 secs] 616757K->616695K(2029056K), [Metaspace: 4582K->4582K(1056768K)], 0.7756442 secs] [Times: user=0.77 sys=0.00, real=0.78 secs]";
        CMSGCAction action = new CMSGCAction();
        System.out.println(action.action(message));
    }

    @Test
    public void testCMSGCAction() {
        String message = "2019-03-18T15:03:39.204-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 28020K(42092K)] 28020K(53868K), 0.0007546 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]" +
                "2019-03-18T15:03:39.205-0800: [CMS-concurrent-mark-start]" +
                "2019-03-18T15:03:39.224-0800: [CMS-concurrent-mark: 0.019/0.019 secs] [Times: user=0.05 sys=0.00, real=0.02 secs]" +
                "2019-03-18T15:03:39.224-0800: [CMS-concurrent-preclean-start]" +
                "2019-03-18T15:03:39.244-0800: [CMS-concurrent-preclean: 0.020/0.020 secs] [Times: user=0.04 sys=0.00, real=0.02 secs]" +
                "2019-03-18T15:03:39.245-0800: [GC (CMS Final Remark) [YG occupancy: 0 K (11776 K)]2019-03-18T15:03:39.245-0800: [Rescan (parallel) , 0.0007508 secs]2019-03-18T15:03:39.245-0800: [weak refs processing, 0.0000221 secs]2019-03-18T15:03:39.245-0800: [class unloading, 0.0011218 secs]2019-03-18T15:03:39.247-0800: [scrub symbol table, 0.0007869 secs]2019-03-18T15:03:39.247-0800: [scrub string table, 0.0002856 secs][1 CMS-remark: 28020K(42092K)] 28020K(53868K), 0.0030433 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]" +
                "2019-03-18T15:03:39.248-0800: [CMS-concurrent-sweep-start]" +
                "2019-03-18T15:03:39.248-0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]" +
                "2019-03-18T15:03:39.248-0800: [CMS-concurrent-reset-start]" +
                "2019-03-18T15:03:39.249-0800: [CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]";
        CMSGCAction action = new CMSGCAction();
        System.out.println(action.action(message));
    }
}
