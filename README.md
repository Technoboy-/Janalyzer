#### 一. 介绍
Janalyzer致力于解析jvm的GC日志，输出易读的方式,外加少量统计功能。目前case仅支持JDK8(1.8.0_191)下7种收集器的日志格式。开发在DEV分支。

#### 二. 依赖
   ```xml
    <dependency>
       <groupId>org.janalyzer</groupId>
       <artifactId>janalyzer</artifactId>
       <version>1.0-SNAPSHOT</version>
    </dependency>
   ```

#### 三. 使用

   
   ```
   输入一段gc日志，可以解析出日志的类型以及更多细节信息。
   ```
   ```java
   public class JanalyzerDemo {
   
       public static void main(String[] args) {
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
           org.janalyzer.JanalyzerDemo janalyzer = JanalyzerFactory.builder().withCollecor(new Collector.All()).build();
           Optional<GCData> analyze = janalyzer.analyze(message);
           System.out.println(analyze.get());
       }
   }
   ```
   ```
    根据builder选择生成gc日志的回收器, Collector参数为young区以及old的回收器类型。
    JanalyzerFactory.builder().withCollecor(new Collector<>(GCType.PARNEW, GCType.CMS)).build();
    
   ```
   |参数|说明|
   |:----:|:----:|
   |type|回收器类型|
   |phases|CMS，G1回收器会用到|
   |HeapSize|堆大小|
   |HeapUsageAfter|回收后堆大小|
   |HeapUsageBefore|回收前堆大小|
   |XXXDuration|触发该次GC总耗时|
   |XXXCaution|引起GC的原因，Allocation Failure表示分配内存失败|
   |YoungSize|年轻代大小|
   |YoungUsageAfter|年轻代回收前大小|
   |YoungUsageBefore|年轻代回收前大小|
   |OldSize|年老代大小|
   |OldUsageAfter|年老代回收前大小|
   |OldUsageBefore|年老代回收前大小|
   |EdenUsage|新生代当前使用大小|
   |EdenUsageBefore|新生代回收前大小|
   |EdenUsageAfter|新生代回收后大小|
   |EdenSize|新生代大小|
   |SurvivorBefore|survivor区回收前大小|
   |SurvivorAfter|survivor区回收后大小|
   

#### 四. 关于更多JDK版本的支持
   由于当前版本仅支持1.8.0_191，遇到其他版本无法解析，请fork项目，然后把相应日志格式放到log目录下，请以jdk版本号命令文件夹，然后merge request。
   或直接通过邮件联系technoboy@yeah.net。