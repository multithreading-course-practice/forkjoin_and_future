package org.example.stage4;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.RecursiveTask;

import static java.lang.Thread.sleep;

@Log4j2
public class ForkJoinBasic {

    public static void main(String[] args) throws InterruptedException {

        Fibonacci fibbonaci = new Fibonacci(100, null);

        fibbonaci.fork();
        //sleep(1000);
        Integer result = fibbonaci.join();

    }

}

@Log4j2
@Getter
class Fibonacci extends RecursiveTask<Integer> {
    final int n;

    final Fibonacci parent;

    Fibonacci(int n, Fibonacci parent) {
        this.n = n;
        this.parent = parent;
        log.info("Create {}, [{}]", n, printLinks());
    }

    public StringBuffer printLinks() {
        Fibonacci current = this;
        StringBuffer sb = new StringBuffer();
        while (current != null) {
            sb.append(current.getN()).append("<");
            current = current.getParent();
        }
        return sb;
    }

    protected Integer compute() {
        log.info("Compute {}", n);
        if (n <= 1) return n;
        Fibonacci f1 = new Fibonacci(n - 1, this);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2, this);
        return f2.compute() + f1.join();
    }
}
