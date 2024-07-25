package org.example.stage1;

import lombok.extern.log4j.Log4j2;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

@Log4j2
public class ThreadStates {
    public static void main(String[] args) {
        Thread newThread = new Thread(() -> {
            log.info("new thread");
        });
        log.info("State of new = {}", newThread.getState());

        Thread runnableThread = startThread("runnable", () -> {
            synchronized (newThread) {
                while (true) {
                }
            }
        });
        log.info("State of runnable = {}", runnableThread.getState());

        Thread waitingThread = startThread("waiting", () -> {
            synchronized (runnableThread) {
                try {
                    runnableThread.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        log.info("State of waiting = {}", waitingThread.getState());

        Thread timeWaiting = startThread("timeWaiting", () -> {
            synchronized (waitingThread) {
                try {
                    waitingThread.wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        log.info("State of timeWaiting = {}", timeWaiting.getState());


            Thread blocked = startThread("blocked", () -> {
                synchronized (newThread) {
                    while (true) {
                    }
                }
            });

            log.info("State of blocked = {}", blocked.getState());

            Thread terminated = startThread("terminated", () -> {});
            log.info("State of terminated = {}", terminated.getState());

            someSleep();
            exit(0);
        }

        private static Thread startThread (String name, Runnable runnable){
            Thread thread = new Thread(runnable);
            thread.setName(name);
            thread.start();
            someSleep();
            return thread;
        }

        private static void someSleep () {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
