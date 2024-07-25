package org.example.stage5;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;
import java.util.function.Function;

import static java.lang.Thread.sleep;

@Log4j2
public class CompletableFuture_Basic_6 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String res = CompletableFuture.supplyAsync(() -> {
                    log.info("Hello start");
                    someSleep(1000);
                    log.info("Hello end");
                    return "Hello";
                })
                .applyToEither(timeoutFuture(2000), Function.identity())
                .exceptionally((e) -> {
                    log.error(e);
                    return null;
                })
                // .thenAccept(log::info).join();
                .get();
        log.info("main end with {}", res);

        scheduler.shutdown();


    }

    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static CompletableFuture<String> timeoutFuture(long delay) {
        CompletableFuture<String> futureTimeout = new CompletableFuture<>();
        scheduler.schedule(() -> futureTimeout.completeExceptionally(new TimeoutException("timeout")), delay, TimeUnit.MILLISECONDS);
        return futureTimeout;
    }

    public static void someSleep(int millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
