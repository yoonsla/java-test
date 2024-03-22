package com.example.java.thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ThreadLocalTest {

    @RequiredArgsConstructor
    public static class YoonThread extends Thread {

        private static final ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "yoonsla");
        private final String name;

        @Override
        public void run() {
            System.out.printf("[%s] Started, ThreadLocal: %s%n", name, threadLocal.get());
            threadLocal.set(name);
            System.out.printf("[%s] Finished, ThreadLocal: %s%n", name, threadLocal.get());
        }
    }

    public void runTest() {
        for (int threadCount = 1; threadCount <= 5; threadCount++) {
            final YoonThread thread = new YoonThread("thread-" + threadCount);
            thread.start();
        }
    }
}
