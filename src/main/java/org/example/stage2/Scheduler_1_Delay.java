package org.example.stage2;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
public class Scheduler_1_Delay {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        log.info("scheduler started");
        executor.schedule(()->{log.info("task 1 executed");}, 1, TimeUnit.SECONDS);
        executor.schedule(()->{log.info("task 2 executed");}, 2, TimeUnit.SECONDS);
        executor.schedule(()->{log.info("task 3 executed");}, 3, TimeUnit.SECONDS);
    }
}
