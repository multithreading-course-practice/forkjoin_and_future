package org.example.stage5;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

@Log4j2
public class CompletableFuture_Basic_5 {

    public static void main(String[] args) {

        CompletableFuture<String> futureHello = CompletableFuture.supplyAsync(() -> {
            log.info("Hello start");
            someSleep(1000);
            log.info("Hello end");
            return "Hello";
        });


        CompletableFuture<String> futureWorld = CompletableFuture.supplyAsync(() -> {
            log.info("World start");
            someSleep(500);
            log.info("World end");
            return "World";
        });

//        futureHello.applyToEither(futureWorld, String::toUpperCase)
//                .thenAccept(log::info)
//                .join();

        futureWorld.applyToEither(futureHello, String::toLowerCase)
                .thenAccept(log::info)
                .thenRun(()->log.info("Run after either"))
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
