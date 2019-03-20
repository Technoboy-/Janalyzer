import org.janalyzer.gc.serial.SerialGCAction;
import org.janalyzer.gc.serial.SerialOldGCAction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Tboy
 */
public class SerialTest {


    /**
     * -XX:+PrintGCDetails -XX:+UseSerialGC -Xms5m -XX:+PrintGCDateStamps
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
    public void testSerial() {
        String message = "[GC (Allocation Failure) 2019-03-19T15:28:10.319-0800: [DefNew: 937K->0K(1920K), 0.0014898 secs] 2631K->2631K(6016K), 0.0015103 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]";

        SerialGCAction phase = new SerialGCAction();

        System.out.println(phase.action(message));
    }

    @Test
    public void testSerialOld() {
        String message = "2019-03-19T15:29:06.077-0800: [Full GC (Allocation Failure) 2019-03-19T15:29:06.077-0800: [Tenured: 616743K->616682K(1398144K), 0.7188938 secs] 616743K->616682K(2000064K), [Metaspace: 4582K->4582K(1056768K)], 0.7190180 secs] [Times: user=0.72 sys=0.00, real=0.72 secs]";

        SerialOldGCAction phase = new SerialOldGCAction();

        System.out.println(phase.action(message));
    }
}
