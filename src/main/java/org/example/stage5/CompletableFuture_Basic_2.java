package org.example.stage5;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;


@Log4j2
public class CompletableFuture_Basic_2 {

    public static void main(String[] args) {

        final CompletableFuture<String> futureHello = CompletableFuture.supplyAsync(() -> {
            log.info("Hello start");
            someSleep(5000);
            log.info("Hello end");
            return "Hello";
        });

        futureHello.thenApply(String::toUpperCase)
                .thenAccept(log::info)
                .join();
    }

    public static void someSleep(int millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
