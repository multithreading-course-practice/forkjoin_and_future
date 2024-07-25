package org.example.stage5;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

@Log4j2
public class CompletableFuture_Basic_4 {

    public static void main(String[] args) {

        CompletableFuture<String> futureHello = CompletableFuture.supplyAsync(() -> {
            log.info("Hello start");
            someSleep(1000);
            log.info("Hello end");
            return "Hello";
        });


        CompletableFuture<String> futureWorld = CompletableFuture.supplyAsync(() -> {
            log.info("World start");
            someSleep(2000);
            log.info("World end");
            return "World";
        });

        CompletableFuture.allOf(futureHello, futureWorld).join();

        futureHello
//                .thenCombine(futureWorld, (hello, world) -> {
//                    log.info("Combine {} {}", hello, world);
//                    return hello + " " + world;
//                })
                .thenCombineAsync(futureWorld, (hello, world) -> {
                    log.info("Combine {} {}", hello, world);
                    return hello + " " + world;
                }, Executors.newSingleThreadExecutor())
                .thenApply(s -> {
                    log.info("Apply {} + myAppend", s);
                    return s + " myAppend";
                })
                .thenAccept(s-> {
                    log.info("Accept {}", s);
                }).join();

    }

    public static void someSleep(int millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
