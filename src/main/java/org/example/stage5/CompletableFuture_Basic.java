package org.example.stage5;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

/**
 * https://kicsikrumpli.github.io/til/completablefuture/2018/01/30/completable-future-cheat-sheet.html
 */
@Log4j2
public class CompletableFuture_Basic {

    public static void main(String[] args) {

        final CompletableFuture<String> futureHello = CompletableFuture.supplyAsync(() -> {
            log.info("Hello start");
            someSleep(1000);
            log.info("Hello end");
            return "Hello";
        });

        try {
            log.info("Get result: {}", futureHello.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        CompletableFuture.supplyAsync(() -> {
            log.info("Second start");
            someSleep(1000);
            log.info("Second end");
            return "second";
        }).thenRun(() -> {
            log.info("Run without result");
        }).join();


        CompletableFuture.supplyAsync(() -> {
                    log.info("Third start");
                    someSleep(1000);
                    if (true) {
                        throw new IllegalStateException("my exception");
                    }
                    return "third";
                })
                .thenAccept(s -> log.info("Run with result: {}", s))
                .exceptionally(e -> {
                    log.error("Exceptionally", e);
                    return null;
                })
                .join();


        log.info("main end");

    }

    public static void someSleep(int millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
