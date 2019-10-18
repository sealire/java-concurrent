package org.leesia.concurrent.utility.semaphore;

import org.leesia.concurrent.thread.RunnableFactory;
import org.leesia.concurrent.util.FunctionUtil;
import org.leesia.concurrent.util.RandomUtil;

import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SemaphoreTest {

    private static Logger LOGGER = LoggerFactory.getLogger(SemaphoreTest.class);

    private static SemaphoreService semaphoreService = new SemaphoreService();

    public static void main(String[] args) {
        test1_1_interrupt_random();

        LOGGER.info("main exit");
    }

    /**
     * 5个线程竞争1个信号量
     */
    public static void test1_1() {
        run(5, x ->  {
            try {
                semaphoreService.syn1_1(FunctionUtil.newSleepFunction(2000));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 5个线程竞争1个信号量，随机中断一些线程
     */
    public static void test1_1_interrupt_random() {
        int threadCount = 5;
        Thread[] threads = run(threadCount, x ->  {
            try {
                semaphoreService.syn1_1(FunctionUtil.newSleepFunction(2000));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });

        int count = RandomUtil.randomInt(1, threadCount, true);
        interrupt(threads, count);

    }

    /**
     * 5个线程竞争2个信号量
     */
    public static void test2_1() {
        run(5, x ->  {
            try {
                semaphoreService.syn2_1(FunctionUtil.newSleepFunction(2000));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 10个线程竞争10个信号量，每个线程竞争3个信号量
     */
    public static void test10_3() {
        run(10, x ->  {
            try {
                semaphoreService.syn10_3(FunctionUtil.newSleepFunction(2000));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    private static Thread[] run(int threadCount, Function function) {
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(RunnableFactory.newRunnable(function));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        return threads;
    }

    private static void interrupt(Thread[] threads, int count) {
        Set<Integer> indexes = RandomUtil.randomSet(0, threads.length - 1, count);
        for (Integer index : indexes) {
            threads[index].interrupt();
            LOGGER.info("interrupt thread: {} ", Thread.currentThread().getName());
        }
    }
}

