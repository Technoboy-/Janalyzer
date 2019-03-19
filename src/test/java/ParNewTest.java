import org.janaylzer.gc.GCData;
import org.janaylzer.gc.cms.CMSGCAction;
import org.janaylzer.gc.cms.phase.*;
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
public class ParNewTest {


    /**
     * 可参考cms的minor gc log 或以下参数
     * -XX:+PrintGCDetails -XX:+UseParNewGC -Xms5m -XX:+PrintGCDateStamps
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

    /**
     * 分为三种格式
     *
     */
    @Test
    public void testParNew() {
        String message1 = "2019-03-18T15:03:39.072-0800: [GC (Allocation Failure) 2019-03-18T15:03:39.072-0800: [ParNew: 36K->4K(3456K), 0.0014085 secs]2019-03-18T15:03:39.073-0800: [CMS: 9226K->6060K(15380K), 0.0137187 secs] 9262K->6060K(18836K), [Metaspace: 4702K->4702K(1056768K)], 0.0152057 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]";
        String message2 = "2018-04-12T13:48:26.134+0800: 15578.050: [GC2018-04-12T13:48:26.135+0800: 15578.050: [ParNew: 3412467K->59681K(3774912K), 0.0971990 secs] 9702786K->6354533K(24746432K), 0.0974940 secs] [Times: user=0.95 sys=0.00, real=0.09 secs]";
        String message3 = "2019-03-19T11:33:45.121-0800: [GC (Allocation Failure) 2019-03-19T11:33:45.121-0800: [ParNew: 0K->2K(40320K), 0.0087214 secs]2019-03-19T11:33:45.130-0800: [Tenured: 90839K->54785K(143588K), 0.0617638 secs] 90840K->54785K(183908K), [Metaspace: 4579K->4579K(1056768K)], 0.0705876 secs] [Times: user=0.08 sys=0.00, real=0.07 secs]";

        ParNewGCAction phase = new ParNewGCAction();

        System.out.println(phase.action(message1));
    }


}
