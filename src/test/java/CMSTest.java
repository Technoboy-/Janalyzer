import org.janaylzer.gc.GCData;
import org.janaylzer.gc.cms.*;
import org.janaylzer.gc.cms.phase.*;
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
        GCData data = new GCData();
        CMSInitialMarkPhase phase = new CMSInitialMarkPhase();
        phase.action(message, data);
        System.out.println(data);
    }

    @Test
    public void testConcurrentMark() {
        String message = "2019-03-18T15:03:39.501-0800: [CMS-concurrent-mark: 0.041/0.040 secs] [Times: user=0.11 sys=0.01, real=0.04 secs]";
        CMSConcurrentMarkPhase phase = new CMSConcurrentMarkPhase();
        GCData data = new GCData();
        phase.action(message, data);
        System.out.println(data);
    }

    @Test
    public void testConcurrentPreclean() {
        String message = "2019-03-18T15:03:39.922-0800: [CMS-concurrent-preclean: 0.059/0.080 secs] [Times: user=0.13 sys=0.00, real=0.07 secs]";
        CMSConcurrentPrecleanPhase phase = new CMSConcurrentPrecleanPhase();
        GCData data = new GCData();
        phase.action(message, data);
        System.out.println(data);
    }


    @Test
    public void testConcurrentAbortablePreclean() {
        String message = "2019-03-18T15:03:39.501-0800: [CMS-concurrent-abortable-preclean: 0.167/1.074 secs] [Times: user=0.20 sys=0.00, real=1.07 secs]";
        CMSConcurrentAbortablePrecleanPhase phase = new CMSConcurrentAbortablePrecleanPhase();
        GCData data = new GCData();
        phase.action(message, data);
        System.out.println(data);
    }

    @Test
    public void testFinalRemark() {
        String message = "2019-03-18T15:03:40.411-0800: [GC (CMS Final Remark) [YG occupancy: 0 K (68608 K)]2019-03-18T15:03:40.411-0800: [Rescan (parallel) , 0.0002597 secs]2019-03-18T15:03:40.411-0800: [weak refs processing, 0.0000120 secs]2019-03-18T15:03:40.411-0800: [class unloading, 0.0005750 secs]2019-03-18T15:03:40.412-0800: [scrub symbol table, 0.0004445 secs]2019-03-18T15:03:40.412-0800: [scrub string table, 0.0001678 secs][1 CMS-remark: 136519K(152420K)] 136520K(221028K), 0.0015073 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] ";
        CMSFinalRemarkPhase phase = new CMSFinalRemarkPhase();
        GCData data = new GCData();
        phase.action(message, data);
        System.out.println(data);
    }

    @Test
    public void testConcurrentSweep() {
        String message = "2019-03-18T15:03:39.548-0800: [CMS-concurrent-sweep: 0.002/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]";
        CMSConcurrentSweepPhase phase = new CMSConcurrentSweepPhase();
        GCData data = new GCData();
        phase.action(message, data);
        System.out.println(data);
    }

    @Test
    public void testConcurrentReset() {
        String message = "2019-03-18T15:03:39.549-0800: [CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] ";
        CMSConcurrentResetPhase phase = new CMSConcurrentResetPhase();
        GCData data = new GCData();
        phase.action(message, data);
        System.out.println(data);
    }

    @Test
    public void testCMSGCAction() {
        String message = "2018-04-12T13:48:26.233+0800: 15578.148: [GC [1 CMS-initial-mark: 6294851K(20971520K)] 6354687K(24746432K), 0.0466580 secs] [Times: user=0.04 sys=0.00, real=0.04 secs]\n" +
                "2018-04-12T13:48:26.280+0800: 15578.195: [CMS-concurrent-mark-start]\n" +
                "2018-04-12T13:48:26.418+0800: 15578.333: [CMS-concurrent-mark: 0.138/0.138 secs] [Times: user=1.01 sys=0.21, real=0.14 secs]\n" +
                "2018-04-12T13:48:26.418+0800: 15578.334: [CMS-concurrent-preclean-start]\n" +
                "2018-04-12T13:48:26.476+0800: 15578.391: [CMS-concurrent-preclean: 0.056/0.057 secs] [Times: user=0.20 sys=0.12, real=0.06 secs]\n" +
                "2018-04-12T13:48:26.476+0800: 15578.391: [CMS-concurrent-abortable-preclean-start]\n" +
                "2018-04-12T13:48:29.989+0800: 15581.905: [CMS-concurrent-abortable-preclean: 3.506/3.514 secs] [Times: user=11.93 sys=6.77, real=3.51 secs]\n" +
                "2018-04-12T13:48:29.991+0800: 15581.906: [GC[YG occupancy: 1805641 K (3774912 K)]2018-04-12T13:48:29.991+0800: 15581.906: [GC2018-04-12T13:48:29.991+0800: 15581.906: [ParNew: 1805641K->48395K(3774912K), 0.0826620 secs] 8100493K->6348225K(24746432K), 0.0829480 secs] [Times: user=0.81 sys=0.00, real=0.09 secs]2018-04-12T13:48:30.074+0800: 15581.989: [Rescan (parallel) , 0.0429390 secs]2018-04-12T13:48:30.117+0800: 15582.032: [weak refs processing, 0.0027800 secs]2018-04-12T13:48:30.119+0800: 15582.035: [class unloading, 0.0033120 secs]2018-04-12T13:48:30.123+0800: 15582.038: [scrub symbol table, 0.0016780 secs]2018-04-12T13:48:30.124+0800: 15582.040: [scrub string table, 0.0004780 secs] [1 CMS-remark: 6299829K(20971520K)] 6348225K(24746432K), 0.1365130 secs] [Times: user=1.24 sys=0.00, real=0.14 secs]\n" +
                "2018-04-12T13:48:30.128+0800: 15582.043: [CMS-concurrent-sweep-start]\n" +
                "2018-04-12T13:48:36.638+0800: 15588.553: [GC2018-04-12T13:48:36.638+0800: 15588.554: [ParNew: 3403915K->52142K(3774912K), 0.0874610 secs] 4836483K->1489601K(24746432K), 0.0877490 secs] [Times: user=0.84 sys=0.00, real=0.09 secs]\n" +
                "2018-04-12T13:48:38.412+0800: 15590.327: [CMS-concurrent-sweep: 8.193/8.284 secs] [Times: user=30.34 sys=16.44, real=8.28 secs]\n" +
                "2018-04-12T13:48:38.419+0800: 15590.334: [CMS-concurrent-reset-start]\n" +
                "2018-04-12T13:48:38.462+0800: 15590.377: [CMS-concurrent-reset: 0.044/0.044 secs] [Times: user=0.15 sys=0.10, real=0.04 secs]";
        CMSGCAction action = new CMSGCAction();
        GCData data = new GCData();
        action.action(message, data);
        System.out.println(data);
    }
}
