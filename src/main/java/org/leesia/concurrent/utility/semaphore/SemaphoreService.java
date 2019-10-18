package org.leesia.concurrent.utility.semaphore;

import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class SemaphoreService {

    private Semaphore semaphore_1 = new Semaphore(1);

    private Semaphore semaphore_2 = new Semaphore(2);

    private Semaphore semaphore_10 = new Semaphore(10);

    /**
     * 1个信号量，每次竞争1个
     *
     * @param function
     * @throws InterruptedException
     */
    public void syn1_1(Function function) throws InterruptedException {
        acquire(semaphore_1, 1);

        function.apply(null);

        release(semaphore_1, 1);
    }

    /**
     * 2个信号量，每次竞争1个
     *
     * @param function
     * @throws InterruptedException
     */
    public void syn2_1(Function function) throws InterruptedException {
        acquire(semaphore_2, 1);

        function.apply(null);

        release(semaphore_2, 1);
    }

    /**
     * 10个信号量，每次竞争3个
     * @param function
     * @throws InterruptedException
     */
    public void syn10_3(Function function) throws InterruptedException {
        acquire(semaphore_10, 3);

        function.apply(null);

        release(semaphore_10, 3);
    }

    /**
     * 获取信号量
     *
     * @param semaphore
     * @param permits
     * @throws InterruptedException
     */
    public void acquire(Semaphore semaphore, int permits) throws InterruptedException {
        if (permits < 2) {
            semaphore.acquire();
        } else {
            semaphore.acquire(permits);
        }
    }

    /**
     * 获取信号量，不可中断
     *
     * @param semaphore
     * @param permits
     */
    public void acquireUninterruptibly(Semaphore semaphore, int permits) {
        if (permits < 2) {
            semaphore.acquireUninterruptibly();
        } else {
            semaphore.acquireUninterruptibly(permits);
        }
    }

    /**
     * 释放信号量
     *
     * @param semaphore
     * @param permits
     */
    public void release(Semaphore semaphore, int permits) {
        if (permits < 2) {
            semaphore.release();
        } else {
            semaphore.release(permits);
        }
    }
}
