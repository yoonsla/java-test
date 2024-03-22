package com.example.java.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ThreadTest {

    public void execute() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> log.info("Hello"));
    }

    public void executorService() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> "Hello");
        String result = future.get();
        log.info(result);
    }

    public void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach(n -> executorService.execute(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
                String thread = Thread.currentThread().getName();
                System.out.println("thread : " + thread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}
