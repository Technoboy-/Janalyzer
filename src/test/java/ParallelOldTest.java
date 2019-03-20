import org.janalyzer.gc.parallel.old.ParallelOldGCAction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Tboy
 */
public class ParallelOldTest {


    /**
     * -XX:+PrintGCDetails -XX:+UseParallelOldGC -Xms5m -XX:+PrintGCDateStamps
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
    public void testParallelOld() {
        String message = "2019-03-19T12:08:20.394-0800: [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(13312K)] [ParOldGen: 616743K->616682K(707072K)] 616743K->616682K(720384K), [Metaspace: 4586K->4586K(1056768K)], 9.3209775 secs] [Times: user=14.04 sys=0.03, real=8.32 secs]";
        String message1 = "2019-03-19T12:08:20.394-0800: [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(13312K)] [ParOldGen: 616743K->616682K(707072K)] 616743K->616682K(720384K), 8.3209775 secs] [Times: user=14.04 sys=0.03, real=8.32 secs]";
        ParallelOldGCAction phase = new ParallelOldGCAction();
        System.out.println(phase.action(message));
    }


}
