import org.janalyzer.Janalyzer;
import org.janalyzer.JanalyzerFactory;
import org.janalyzer.gc.GCData;
import org.janalyzer.util.Collector;
import org.janalyzer.util.Optional;
import org.junit.Test;

import java.util.List;

/**
 * @Author: Tboy
 */
public class JanalyzerDemoTest {

    @Test
    public void testJanalyzer(){

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

        Janalyzer janalyzer = JanalyzerFactory.builder().withCollecor(new Collector.All()).build();
        Optional<List<GCData>> analyze = janalyzer.analyze(message);
        System.out.println(analyze.get());
    }
}
