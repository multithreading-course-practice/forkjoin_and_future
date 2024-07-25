package org.example.stage5;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static java.lang.Thread.sleep;


/**
 * https://snowone.ru/2020/speakers/kuksenko
 */
@Log4j2
public class CompletableFuture_Kuksenko {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<String> future1 = new CompletableFuture<>();
        future1.thenAccept(getConsumer());
        //Регистрация действия до исполнения
        executorService.submit(getTask(future1, "Task 1"));

        future1.join();

        CompletableFuture<String> future2 = new CompletableFuture<>();
        executorService.submit(getTask(future2, "Task 2"));
        //Регистрация действия после исполнения, но не после завершения
        future2.thenAccept(getConsumer());

        future2.join();

        CompletableFuture<String> future3 = new CompletableFuture<>();
        executorService.submit(getTask(future3, "Task 3"));
        //Регистрация действия после исполнения и завершения
        someSleep(2000);
        future3.thenAccept(getConsumer());

        future3.join();

        executorService.shutdown();

    }

    public static Runnable getTask(CompletableFuture<String> future, String result) {
        return () -> {
            someSleep(1000);
            future.complete(result);
        };
    }

    public static Consumer<String> getConsumer() {
        return (s) -> {
            log.info("Result = {}", s);
        };
    }

    public static void someSleep(int millis) {
        try {
            sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
