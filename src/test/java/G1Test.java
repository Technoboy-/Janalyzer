import org.janalyzer.gc.g1.G1GCAction;
import org.janalyzer.gc.g1.phase.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Tboy
 */
public class G1Test {


    /**
     * -XX:+PrintGCDetails -XX:+UseG1GC -Xms5m -XX:+PrintGCDateStamps
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
        String message = "2019-03-20T10:32:16.846-0800: [GC pause (G1 Evacuation Pause) (young), 0.0057688 secs]" +
                "   [Parallel Time: 2.9 ms, GC Workers: 8]" +
                "      [GC Worker Start (ms): Min: 168.8, Avg: 168.9, Max: 169.1, Diff: 0.2]" +
                "      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.4, Max: 1.1, Diff: 1.1, Sum: 3.0]\n" +
                "      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]\n" +
                "         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0.1]\n" +
                "      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]\n" +
                "      [Code Root Scanning (ms): Min: 0.0, Avg: 0.2, Max: 1.0, Diff: 1.0, Sum: 1.9]\n" +
                "      [Object Copy (ms): Min: 0.6, Avg: 1.4, Max: 2.2, Diff: 1.6, Sum: 10.9]\n" +
                "      [Termination (ms): Min: 0.0, Avg: 0.6, Max: 1.0, Diff: 1.0, Sum: 4.9]\n" +
                "         [Termination Attempts: Min: 1, Avg: 5.0, Max: 8, Diff: 7, Sum: 40]\n" +
                "      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]\n" +
                "      [GC Worker Total (ms): Min: 2.5, Avg: 2.6, Max: 2.8, Diff: 0.2, Sum: 20.9]\n" +
                "      [GC Worker End (ms): Min: 171.4, Avg: 171.5, Max: 171.7, Diff: 0.2]\n" +
                "   [Code Root Fixup: 0.0 ms]\n" +
                "   [Code Root Purge: 0.0 ms]\n" +
                "   [Clear CT: 0.2 ms]\n" +
                "   [Other: 2.7 ms]\n" +
                "      [Choose CSet: 0.0 ms]\n" +
                "      [Ref Proc: 2.5 ms]\n" +
                "      [Ref Enq: 0.0 ms]\n" +
                "      [Redirty Cards: 0.1 ms]\n" +
                "      [Humongous Register: 0.0 ms]\n" +
                "      [Humongous Reclaim: 0.0 ms]\n" +
                "      [Free CSet: 0.0 ms]\n" +
                "   [Eden: 3072.0K(3072.0K)->0.0B(2048.0K) Survivors: 0.0B->1024.0K Heap: 3072.0K(6144.0K)->832.3K(6144.0K)]\n" +
                " [Times: user=0.01 sys=0.00, real=0.01 secs]";

        G1InitialMarkGCPhase phase = new G1InitialMarkGCPhase();

        System.out.println(phase.action(message));
    }

    @Test
    public void testRootRegionScan() {

        String message = "2019-03-20T10:32:16.938-0800: [GC concurrent-root-region-scan-end, 0.0004932 secs]";

        G1RootRegionScanGCPhase phase = new G1RootRegionScanGCPhase();

        System.out.println(phase.action(message));
    }

    @Test
    public void testConcurrentMark() {

        String message = "2019-03-20T10:32:16.940-0800: [GC concurrent-mark-end, 0.0021129 secs]";

        G1ConcurrentMarkGCPhase phase = new G1ConcurrentMarkGCPhase();

        System.out.println(phase.action(message));
    }

    @Test
    public void testRemark() {

        String message = "2019-03-20T10:32:16.941-0800: [GC remark 2019-03-20T10:32:16.941-0800: [Finalize Marking, 0.0012042 secs] 2019-03-20T10:32:16.942-0800: [GC ref-proc, 0.0000551 secs] 2019-03-20T10:32:16.942-0800: [Unloading, 0.0009181 secs], 0.0023219 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]";

        G1RemarkGCPhase phase = new G1RemarkGCPhase();

        System.out.println(phase.action(message));
    }

    @Test
    public void testCleanup() {

        String message = "2019-03-20T10:32:16.943-0800: [GC cleanup 2347K->2347K(6144K), 0.0003618 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]";

        G1CleanupGCPhase phase = new G1CleanupGCPhase();

        System.out.println(phase.action(message));
    }

    @Test
    public void testFullGC(){
        String message = "2019-03-20T10:34:36.044-0800: [Full GC (Allocation Failure)  1504M->903M(2048M), 2.7624791 secs]" +
                "   [Eden: 0.0B(99.0M)->0.0B(102.0M) Survivors: 1024.0K->0.0B Heap: 1504.7M(2048.0M)->903.1M(2048.0M)], [Metaspace: 4574K->4574K(1056768K)]" +
                " [Times: user=2.69 sys=0.03, real=2.76 secs]";

        G1GCAction action = new G1GCAction();
        System.out.println(action.action(message));
    }
}
