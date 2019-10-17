package org.leesia.concurrent.executor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class Test  {

    public static void printValur(String str){
        System.out.println("print value : "+str);
    }

    public static void main(String[] args) throws Exception {
        BlockingQueue linkedBlockingDeque = new LinkedBlockingDeque(2);
        BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(3);
        BlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(2);
        BlockingQueue synchronousQueue = new SynchronousQueue();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1, TimeUnit.SECONDS, linkedBlockingQueue, r -> {
            Thread thread = new Thread(r);
            thread.setName("" + System.currentTimeMillis() + " : " + Math.random());
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println(t.getName() + ": my e: " + e);
                }
            });
            return thread;
        }, (r, executor1) -> System.out.println("reject"));

//        executor.allowCoreThreadTimeOut(false);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                if (Thread.currentThread().isInterrupted() == true) {
//                    throw new InterruptedException();
//                }
//                  System.out.println(Thread.currentThread().getName() + " : start : " + new Date());
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                String t = "";
                for (int i = 0; i < 100000000; i++) {
                    t = ""+ i;
//                    if (i > 10000) {
//                        throw new RuntimeException("large");
//                    }
                }
                System.out.println(Thread.currentThread().getName() + " : end : " + new Date());
            }
        };

        executor.prestartCoreThread();
        System.out.println("pool size: " + executor.getPoolSize());

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        Thread.sleep(3000);
        System.out.println();

        System.out.println("completed: " + executor.getCompletedTaskCount());

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        System.out.println("pool size: " + executor.getCorePoolSize());

        executor.shutdown();

        System.out.println("isShutdown " + executor.isShutdown());

        System.out.println(executor.awaitTermination(2, TimeUnit.SECONDS));

        System.out.println("isTerminating " + executor.isTerminating());

        Thread.sleep(1000);
        System.out.println("isTerminated " + executor.isTerminated());

        System.out.println("completed: " + executor.getCompletedTaskCount());

        System.out.println(executor.allowsCoreThreadTimeOut());
    }

    public static void two() {
        Temp command = new Temp();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleWithFixedDelay(command, 0, 3, TimeUnit.SECONDS);
        scheduler.scheduleWithFixedDelay(command, 1, 3, TimeUnit.SECONDS);
    }

    public static void one() {
        List<String> al = Arrays.asList("a", "b", "c", "d");
        al.forEach(Test::printValur);

        Consumer<String> methodParam = Test::printValur;
        al.forEach(x -> methodParam.accept(x));
    }
}


class Temp extends Thread {
    public void run() {
        System.out.println(new Date());
    }
}