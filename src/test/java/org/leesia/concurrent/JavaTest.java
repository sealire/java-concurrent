package org.leesia.concurrent;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class JavaTest  {

    public static void printValur(String str){
        System.out.println("print value : "+str);
    }

    public static void main(String[] args) throws Exception {
        BlockingQueue linkedBlockingDeque = new LinkedBlockingDeque(2);
        BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(2);
        BlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(2);
        BlockingQueue synchronousQueue = new SynchronousQueue();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 1, TimeUnit.SECONDS, arrayBlockingQueue, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("reject");
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": " + new Date());
            }
        };

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
//        executor.execute(runnable);
//        executor.execute(runnable);
//        executor.execute(runnable);
//        executor.execute(runnable);
//        executor.execute(runnable);
//        executor.execute(runnable);

        executor.shutdown();
    }

    public static void two() {
        Temp command = new Temp();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleWithFixedDelay(command, 0, 3, TimeUnit.SECONDS);
        scheduler.scheduleWithFixedDelay(command, 1, 3, TimeUnit.SECONDS);
    }

    public static void one() {
        List<String> al = Arrays.asList("a", "b", "c", "d");
        al.forEach(JavaTest::printValur);

        Consumer<String> methodParam = JavaTest::printValur;
        al.forEach(x -> methodParam.accept(x));
    }
}


class Temp extends Thread {
    public void run() {
        System.out.println(new Date());
    }
}