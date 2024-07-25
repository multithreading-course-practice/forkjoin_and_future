package org.example.stage5;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;


@Log4j2
public class CompletableFuture_Basic_3 {

    public static void main(String[] args) {

        final CompletableFuture<String> futureHello = CompletableFuture.supplyAsync(() -> {
            log.info("Hello start");
            someSleep(1000);
            log.info("Hello end");
            return "Hello";
        });

        boolean isWrapped = false;

        if (isWrapped) {

            CompletableFuture<CompletableFuture<String>> wrapped = futureHello.thenApply((s) -> CompletableFuture.supplyAsync(() -> {
                log.info("Second start");
                someSleep(1000);
                log.info("Second end");
                return s.toUpperCase();
            }));

            wrapped.thenAccept((f -> log.info(f.join())))
                    .join();
        } else {
            CompletableFuture<String> flatted = futureHello.thenCompose(s -> CompletableFuture.supplyAsync(() -> {
                log.info("Second start");
                someSleep(1000);
                log.info("Second end");
                return s.toUpperCase();
            }));

            flatted.thenAccept((f -> log.info(f)))
                    .thenRun(() -> log.info("Finished"))
                    .join();
        }
    }

    public static void someSleep(int millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
