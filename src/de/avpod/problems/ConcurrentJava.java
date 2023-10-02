package de.avpod.problems;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentJava {
    private final CountDownLatch countDownLatch = new CountDownLatch(2);
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private int value = 0;

    public static void main(String[] args) {
        ConcurrentJava concurrentJava = new ConcurrentJava();
        concurrentJava.testMonitor();


    }

    private void testMonitor() {
        Object monitor = new Object();

        Thread thread1 = new Thread(new MonitorRunnable("1", monitor));
        Thread thread2 = new Thread(new MonitorRunnable("2", monitor));
        Thread thread3 = new Thread(new MonitorRunnable("3", monitor));

        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void testSemaphore() {
        Semaphore semaphore = new Semaphore(1, true);

        Thread thread1 = new Thread(new SemaphoreRunnable("1", semaphore));
        Thread thread2 = new Thread(new SemaphoreRunnable("2", semaphore));
        Thread thread3 = new Thread(new SemaphoreRunnable("3", semaphore));

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void testCyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Thread thread1 = new Thread(new CyclicRunnable("1", cyclicBarrier));
        Thread thread2 = new Thread(new CyclicRunnable("2", cyclicBarrier));
        Thread thread3 = new Thread(new CyclicRunnable("3", cyclicBarrier));

        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class CyclicRunnable implements Runnable {
        CyclicBarrier cyclicBarrier;
        String name;

        public CyclicRunnable(final String name, final CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        public void run() {
            System.out.println("Wait %s".formatted(name));
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                System.out.println("Interrupted %s".formatted(name));
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                System.out.println("Broken %s".formatted(name));
                throw new RuntimeException(e);
            }

            System.out.println("Wait over %s".formatted(name));
        }
    }

    private class SemaphoreRunnable implements Runnable {
        Semaphore semaphore;
        String name;

        public SemaphoreRunnable(final String name, final Semaphore semaphore) {
            this.semaphore = semaphore;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println("Before acquire %s".formatted(name));
                semaphore.acquire(1);
                System.out.println("After acquire %s".formatted(name));
                System.out.println("Release %s".formatted(name));
                semaphore.release();
            } catch (InterruptedException e) {
                System.out.println("Interrupted %s".formatted(name));
                throw new RuntimeException(e);
            }
        }
    }

    private class MonitorRunnable implements Runnable {
        private final String name;
        private final Object monitor;

        public MonitorRunnable(final String name, final Object monitor) {
            this.name = name;
            this.monitor = monitor;
        }

        @Override
        public void run() {
            System.out.println("Before synchronized %s".formatted(name));
            synchronized (monitor) {
                System.out.println("Inside of synchronized %s value=%d".formatted(name, value));
            }
            value++;
            System.out.println("After synchronized %s value=%d".formatted(name, value));
        }
    }
}
