import com.payam.test.counters.NamedCounters;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTest {

    public final static int THREAD_POOL_SIZE = 100;
    public final static int NUMBEROFITTERATION = 100;
    public final static int NUMBEROFKEYS = 500;
    public final static int NUMBEROFITTERATIONS = 50000;

    public static NamedCounters nc = null;

    public static void main(String[] args) throws InterruptedException {
        NamedCounters nc = new NamedCounters();
        nc.printCounters();
        System.out.print("Now testing....");
        performTest(nc);
    }

    public static void performTest(final NamedCounters counters) throws InterruptedException {
        for (int i = 0; i < NUMBEROFITTERATION; i++) {
            ExecutorService execServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            for (int j = 0; j < THREAD_POOL_SIZE; j++) {
                execServer.execute(new Runnable() {
                    @SuppressWarnings("unused")
                    @Override
                    public void run() {

                        for (int i = 1; i < NUMBEROFKEYS; i++) {
                            Integer oneMoreCounter = counters.add(String.valueOf(i));
                        }

                        for (int i = 1; i < NUMBEROFITTERATIONS; i++) {
                            String  oneCounter = String.valueOf( i % NUMBEROFKEYS );
                            Integer readBefore = counters.get(oneCounter);
                            if (readBefore == null) {
                                continue;
                            }
                            Integer increment = counters.inc(String.valueOf(oneCounter));
                            Integer readAfter = counters.get(oneCounter);
                            Integer diff = readAfter - readBefore;
                            if (diff > 1) {
                                System.out.println("counter: " + oneCounter + " Before: " + readBefore + " After: " + readAfter + " Diff: " + diff + "\n");
                            }
                        }
                    }
                });
            }
            execServer.shutdown();
            execServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        }

        Integer expect = (NUMBEROFITTERATIONS / NUMBEROFKEYS) *  NUMBEROFITTERATION * THREAD_POOL_SIZE;
        counters.printCounters();
        boolean failed = false;
        for (Map.Entry<String, Integer> entry : counters.scanI().entrySet()) {
            if (entry.getValue().intValue() != expect ) {
                failed = true ;
                System.out.println("FAILED:" + entry.getKey() + " = " + entry.getValue() + ":" +  expect.toString());
            }
        }
        if (failed) {
            System.out.println("FAILED");
        }
        else {
            System.out.println("SUCCESS");
        }
    }
}

