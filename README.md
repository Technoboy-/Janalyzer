#### 一. 介绍
Janalyzer致力于解析jvm的GC日志，输出易读的方式,外加少量统计功能。目前case仅支持JDK8(1.8.0_191),开发在DEV分支。

#### 二. 使用
Serial日志
   ```java
   public class SerialTest {
       @Test
       public void testSerial() {
            String message = "[GC (Allocation Failure) 2019-03-19T15:28:10.319-0800: [DefNew: 937K->0K(1920K), 0.0014898 secs] 2631K->2631K(6016K), 0.0015103 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]";
            SerialAction phase = new SerialAction();
            System.out.println(phase.action(message));
       }
   }
   ```
   ```
   输出
   Optional[GCData{type=SERIAL, phases=[], properties={HeapSize=6016K, HeapUsageAfter=2631K, HeapUsageBefore=2631K, SerialCaution=Allocation Failure, SerialDuration=0.0015103, SerialYoungDuration=0.0014898, YoungSize=1920K, YoungUsageAfter=0K, YoungUsageBefore=937K}}]
   ```
   |参数|说明|
   |:----:|:----:|
   |type|回收器类型-SERIAL|
   |phases|CMS回收器会用到|
   |HeapSize|堆大小|
   |HeapUsageAfter|回收后堆大小|
   |HeapUsageBefore|回收前堆大小|
   |SerialDuration|触发该次GC总耗时|
   |SerialYoungDuration|回收年轻代耗时|
   |SerialCaution|引起SerialGC的原因，Allocation Failure表示分配内存失败|
   |YoungSize|年轻代大小|
   |YoungUsageAfter|年轻代回收前大小|
   |YoungUsageBefore|年轻代回收前大小|
   
SerialOld日志
   
   ```java
   public class SerialTest {
       @Test
       public void testSerialOld() {
           String message = "2019-03-19T15:29:06.077-0800: [Full GC (Allocation Failure) 2019-03-19T15:29:06.077-0800: [Tenured: 616743K->616682K(1398144K), 0.7188938 secs] 616743K->616682K(2000064K), [Metaspace: 4582K->4582K(1056768K)], 0.7190180 secs] [Times: user=0.72 sys=0.00, real=0.72 secs]";
           SerialOldAction phase = new SerialOldAction();
           System.out.println(phase.action(message));
       }
    
   }
   ```
   ```
   输出
   Optional[GCData{type=SERIAL_OLD, phases=[], properties={HeapSize=2000064K, HeapUsageAfter=616682K, HeapUsageBefore=616743K, OldSize=1398144K, OldUsageAfter=616682K, OldUsageBefore=616743K, SerialOLDCleanupOldDuration=0.7188938, SerialOLDDuration=0.7190180, SerialOldCaution=Allocation Failure}}]
   ```
   |参数|说明|
   |:----:|:----:|
   |type|回收器类型-SERIAL_OLD|
   |phases|CMS回收器会用到|
   |HeapSize|堆大小|
   |HeapUsageAfter|回收后堆大小|
   |HeapUsageBefore|回收前堆大小|
   |SerialOLDDuration|触发该次GC总耗时|
   |SerialOLDCleanupOldDuration|回收年老代耗时|
   |SerialOldCaution|引起SerialOLDGC的原因，Allocation Failure表示分配内存失败|
   |OldSize|年老代大小|
   |OldUsageAfter|年老代回收前大小|
   |OldUsageBefore|年老代回收前大小|

ParNew日志
   ```java
   public class ParNewTest {
        @Test
        public void testParNew() {
            String message1 = "2019-03-18T15:03:39.072-0800: [GC (Allocation Failure) 2019-03-18T15:03:39.072-0800: [ParNew: 36K->4K(3456K), 0.0014085 secs]2019-03-18T15:03:39.073-0800: [CMS: 9226K->6060K(15380K), 0.0137187 secs] 9262K->6060K(18836K), [Metaspace: 4702K->4702K(1056768K)], 0.0152057 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]";
            String message2 = "2018-04-12T13:48:26.134+0800: 15578.050: [GC2018-04-12T13:48:26.135+0800: 15578.050: [ParNew: 3412467K->59681K(3774912K), 0.0971990 secs] 9702786K->6354533K(24746432K), 0.0974940 secs] [Times: user=0.95 sys=0.00, real=0.09 secs]";
            String message3 = "2019-03-19T11:33:45.121-0800: [GC (Allocation Failure) 2019-03-19T11:33:45.121-0800: [ParNew: 0K->2K(40320K), 0.0087214 secs]2019-03-19T11:33:45.130-0800: [Tenured: 90839K->54785K(143588K), 0.0617638 secs] 90840K->54785K(183908K), [Metaspace: 4579K->4579K(1056768K)], 0.0705876 secs] [Times: user=0.08 sys=0.00, real=0.07 secs]";
            ParNewGCAction phase = new ParNewGCAction();
            System.out.println(phase.action(message1));
        }
   }
   ```
   ```
    输出
    Optional[GCData{type=PARNEW, phases=[], properties={HeapSize=18836K, HeapUsageAfter=6060K, HeapUsageBefore=9262K, ParNewCaution=Allocation Failure, ParNewCleanupDuration=0.0014085, ParNewDuration=0.0152057, YoungSize=3456K, YoungUsageAfter=4K, YoungUsageBefore=36K}}]
   ```
   |参数|说明|
   |:----:|:----:|
   |type|回收器类型-PARNEW|
   |phases|CMS回收器会用到|
   |HeapSize|堆大小|
   |HeapUsageAfter|回收后堆大小|
   |HeapUsageBefore|回收前堆大小|
   |ParNewCaution|触发该次GC总耗时|
   |ParNewCleanupDuration|回收年老代耗时|
   |ParNewCaution|引起PARNEW GC的原因，Allocation Failure表示分配内存失败|
   |YoungSize|年轻代大小|
   |YoungUsageAfter|年轻代回收前大小|
   |YoungUsageBefore|年轻代回收前大小|
   
ParallelScavenge日志
   ```java
   public class ParallelScavengeTest {
       @Test
       public void testParallelScavenge() {
            String message = "2019-03-19T11:40:47.801-0800: [GC (Allocation Failure) [PSYoungGen: 4497K->448K(4608K)] 1233088K->1229039K(1402880K), 0.8331384 secs] [Times: user=4.98 sys=0.40, real=0.83 secs]";
            ParallelScavengeAction phase = new ParallelScavengeAction();
            System.out.println(phase.action(message));
       }
   }
   ```
   ```
   输出
   Optional[GCData{type=PARALLEL_SCAVENGE, phases=[], properties={HeapSize=1402880K, HeapUsageAfter=1229039K, HeapUsageBefore=1233088K, ParallelScavengeCaution=Allocation Failure, ParallelScavengeDuration=0.8331384, YoungSize=4608K, YoungUsageAfter=448K, YoungUsageBefore=4497K}}]
   ```
   |参数|说明|
   |:----:|:----:|
   |type|回收器类型-PARALLEL_SCAVENGE|
   |phases|CMS回收器会用到|
   |HeapSize|堆大小|
   |HeapUsageAfter|回收后堆大小|
   |HeapUsageBefore|回收前堆大小|
   |ParallelScavengeDuration|触发该次GC耗时|
   |ParallelScavengeCaution|引起PARALLEL_SCAVENGE GC的原因，Allocation Failure表示分配内存失败|
   |YoungSize|年轻代大小|
   |YoungUsageAfter|年轻代回收前大小|
   |YoungUsageBefore|年轻代回收前大小|

ParallelOld日志
   ```java
   public class ParallelOldTest {
        @Test
        public void testParallelOld() {
            String message = "2019-03-19T12:08:20.394-0800: [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(13312K)] [ParOldGen: 616743K->616682K(707072K)] 616743K->616682K(720384K), [Metaspace: 4586K->4586K(1056768K)], 9.3209775 secs] [Times: user=14.04 sys=0.03, real=8.32 secs]";
            String message1 = "2019-03-19T12:08:20.394-0800: [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(13312K)] [ParOldGen: 616743K->616682K(707072K)] 616743K->616682K(720384K), 8.3209775 secs] [Times: user=14.04 sys=0.03, real=8.32 secs]";
            ParallelOldAction phase = new ParallelOldAction();
            System.out.println(phase.action(message));
        }
   }
   ```
   ```
    输出
    Optional[GCData{type=PARALLEL_OLD, phases=[], properties={HeapSize=720384K, HeapUsageAfter=616682K, HeapUsageBefore=616743K, OldSize=707072K, OldUsageAfter=616682K, OldUsageBefore=616743K, ParallelOldCaution=Allocation Failure, ParallelOldDuration=9.3209775, YoungSize=13312K, YoungUsageAfter=0K, YoungUsageBefore=0K}}]
   ```
   |参数|说明|
   |:----:|:----:|
   |type|回收器类型-PARALLEL_OLD|
   |phases|CMS回收器会用到|
   |HeapSize|堆大小|
   |HeapUsageAfter|回收后堆大小|
   |HeapUsageBefore|回收前堆大小|
   |ParallelOldDuration|触发该次GC耗时|
   |ParallelOldCaution|引起PARALLEL_SCAVENGE GC的原因，Allocation Failure表示分配内存失败|
   |YoungSize|年轻代大小|
   |YoungUsageAfter|年轻代回收前大小|
   |YoungUsageBefore|年轻代回收前大小|
   |OldSize|年老代大小|
   |OldUsageAfter|年老代回收前大小|
   |OldUsageBefore|年老代回收前大小|
   
CMS日志
   ```java
   public class CMSTest{
        @Test
        public void testCMSGCAction() {
            String message = "2018-04-12T13:48:26.233+0800: 15578.148: [GC [1 CMS-initial-mark: 6294851K(20971520K)] 6354687K(24746432K), 0.0466580 secs] [Times: user=0.04 sys=0.00, real=0.04 secs]" +
                        "2018-04-12T13:48:26.280+0800: 15578.195: [CMS-concurrent-mark-start]" +
                        "2018-04-12T13:48:26.418+0800: 15578.333: [CMS-concurrent-mark: 0.138/0.138 secs] [Times: user=1.01 sys=0.21, real=0.14 secs]" +
                        "2018-04-12T13:48:26.418+0800: 15578.334: [CMS-concurrent-preclean-start]" +
                        "2018-04-12T13:48:26.476+0800: 15578.391: [CMS-concurrent-preclean: 0.056/0.057 secs] [Times: user=0.20 sys=0.12, real=0.06 secs]" +
                        "2018-04-12T13:48:26.476+0800: 15578.391: [CMS-concurrent-abortable-preclean-start]" +
                        "2018-04-12T13:48:29.989+0800: 15581.905: [CMS-concurrent-abortable-preclean: 3.506/3.514 secs] [Times: user=11.93 sys=6.77, real=3.51 secs]" +
                        "2018-04-12T13:48:29.991+0800: 15581.906: [GC[YG occupancy: 1805641 K (3774912 K)]2018-04-12T13:48:29.991+0800: 15581.906: [GC2018-04-12T13:48:29.991+0800: 15581.906: [ParNew: 1805641K->48395K(3774912K), 0.0826620 secs] 8100493K->6348225K(24746432K), 0.0829480 secs] [Times: user=0.81 sys=0.00, real=0.09 secs]2018-04-12T13:48:30.074+0800: 15581.989: [Rescan (parallel) , 0.0429390 secs]2018-04-12T13:48:30.117+0800: 15582.032: [weak refs processing, 0.0027800 secs]2018-04-12T13:48:30.119+0800: 15582.035: [class unloading, 0.0033120 secs]2018-04-12T13:48:30.123+0800: 15582.038: [scrub symbol table, 0.0016780 secs]2018-04-12T13:48:30.124+0800: 15582.040: [scrub string table, 0.0004780 secs] [1 CMS-remark: 6299829K(20971520K)] 6348225K(24746432K), 0.1365130 secs] [Times: user=1.24 sys=0.00, real=0.14 secs]" +
                        "2018-04-12T13:48:30.128+0800: 15582.043: [CMS-concurrent-sweep-start]" +
                        "2018-04-12T13:48:36.638+0800: 15588.553: [GC2018-04-12T13:48:36.638+0800: 15588.554: [ParNew: 3403915K->52142K(3774912K), 0.0874610 secs] 4836483K->1489601K(24746432K), 0.0877490 secs] [Times: user=0.84 sys=0.00, real=0.09 secs]" +
                        "2018-04-12T13:48:38.412+0800: 15590.327: [CMS-concurrent-sweep: 8.193/8.284 secs] [Times: user=30.34 sys=16.44, real=8.28 secs]" +
                        "2018-04-12T13:48:38.419+0800: 15590.334: [CMS-concurrent-reset-start]" +
                        "2018-04-12T13:48:38.462+0800: 15590.377: [CMS-concurrent-reset: 0.044/0.044 secs] [Times: user=0.15 sys=0.10, real=0.04 secs]";
            CMSGCAction action = new CMSGCAction();
            System.out.println(action.action(message));
        }
   }
   ```
   ```
   输出
   Optional[GCData{type=CMS, phases=[GCPhase{phase=CMS_INITIAL_MARK, properties={CMSInitialMarkDuration=0.0007546, HeapSize=53868K, HeapUsageBefore=28020K, OldSize=42092K, OldUsageBefore=28020K}}, GCPhase{phase=CMS_CONCURRENT_MARK, properties={CMSConcurrentMarkDuration=0.019}}, GCPhase{phase=CMS_CONCURRENT_PRECLEAN, properties={CMSConcurrentPrecleanDuration=0.020}}, GCPhase{phase=CMS_FINAL_REMARK, properties={CMSFinalRemarkDuration=0.0030433, CMSRescanDuration=0.0007508, CMSWeakRefsProcessingDuration=0.0000221, HeapSize=53868K, HeapUsageAfter=28020K, OldSize=42092K, OldUsageAfter=28020K, YoungSize=11776 K, YoungUsage=0 }}, GCPhase{phase=CMS_CONCURRENT_SWEEP, properties={CMSConcurrentSweepDuration=0.001}}, GCPhase{phase=CMS_CONCURRENT_RESET, properties={CMSConcurrentResetDuration=0.001}}], properties={}}]
   ```
   
   |参数|说明|
   |:----:|:----:|
   |type|回收器类型-PARALLEL_OLD|
   |phases|CMS分为7个阶段，每个阶段有不同的工作以及耗时|
  