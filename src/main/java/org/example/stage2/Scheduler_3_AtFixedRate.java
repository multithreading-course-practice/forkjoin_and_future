package org.example.stage2;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@Log4j2
public class Scheduler_3_AtFixedRate {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        log.info("scheduler started");
        executor.scheduleAtFixedRate(()->{
            log.info("task 1 started");
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("task 1 executed");
            }, 0,1, TimeUnit.SECONDS);
    }
}
