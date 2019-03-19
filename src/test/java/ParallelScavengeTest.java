import org.janaylzer.gc.GCData;
import org.janaylzer.gc.parallel.scavenge.ParallelScavengeAction;
import org.janaylzer.gc.parnew.ParNewGCAction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Tboy
 */
public class ParallelScavengeTest {


    /**
     * -XX:+PrintGCDetails -XX:+UseParallelGC -Xms5m -XX:+PrintGCDateStamps
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
    public void testParallelScavenge() {
        String message = "2019-03-19T11:40:47.801-0800: [GC (Allocation Failure) [PSYoungGen: 4497K->448K(4608K)] 1233088K->1229039K(1402880K), 0.8331384 secs] [Times: user=4.98 sys=0.40, real=0.83 secs]";

        GCData data = new GCData();
        ParallelScavengeAction phase = new ParallelScavengeAction();
        phase.action(message, data);
        System.out.println(data);
    }


}
