package org.leesia.concurrent.utility.semaphore;

public class SemaphoreTest {

    public static void main(String[] args) {
        Service service = new Service();
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Run(service));
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}

class Run implements Runnable {

    private Service service;

    public Run(Service service) {
        this.service = service;
    }

    public void run() {
        service.testMethod();
    }
}
