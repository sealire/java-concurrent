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
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("runnable");
                }
            };

            Callable<Double> callable = new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    Thread.sleep(10000);
                    return Math.random();
                }
            };

            MyDouble d = new MyDouble(0);
            Runnable myRun = new MyRun(d);

//        executor.submit(runnable);
            Future<Double> future = executor.submit(callable);
//        Future<MyDouble> myFuture = executor.submit(myRun, d);

            Thread.sleep(1000);
//            System.out.println(future.cancel(false));
            System.out.println(future.get(2, TimeUnit.SECONDS));
            System.out.println(future.isCancelled());
            System.out.println(future.isDone());

//        System.out.println(d.getRate());
//        System.out.println(myFuture.get().getRate());
        } finally {
            executor.shutdown();
        }
    }

    public static void three() throws Exception {
        Thread.currentThread().setName("main");

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
                if (Thread.currentThread().getName().equals("main")) {
                    System.out.println("main do it");

                    return;
                }
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
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());


        executor.prestartCoreThread();
        System.out.println("core size: " + executor.getCorePoolSize());
        System.out.println("max size: " + executor.getMaximumPoolSize());
        System.out.println("pool size: " + executor.getPoolSize());

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        executor.getActiveCount();

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

        Thread.sleep(3000);
        System.out.println();

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        System.out.println("completed: " + executor.getCompletedTaskCount());

        System.out.println("task count: " + executor.getTaskCount());

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

class MyDouble {
    private double rate;

    public MyDouble(double d) {
        this.rate = d;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

class Temp extends Thread {
    public void run() {
        System.out.println(new Date());
    }
}

class MyRun implements Runnable {

    private MyDouble d;

    public MyRun(MyDouble d) {
        this.d = d;
    }

    @Override
    public void run() {
        System.out.println("MyRun");

        d.setRate(Math.random());
    }
}