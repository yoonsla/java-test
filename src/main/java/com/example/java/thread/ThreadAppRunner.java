package com.example.java.thread;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThreadAppRunner implements ApplicationRunner {

    private final ThreadTest threadTest;
    private final ThreadLocalTest threadLocalTest;

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        threadTest.execute();
//        threadTest.executorService();
//        threadTest.test();
//        threadLocalTest.runTest();
    }
}
