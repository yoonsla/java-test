package com.example.java;

import com.example.java.future.CallbackTest;
import com.example.java.future.FutureTest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final FutureTest futureTest;
    private final CallbackTest callbackTest;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        futureTest.executor();
//        futureTest.executor();
//        futureTest.submit();
//        futureTest.future();
//        futureTest.futureTask();
//        futureTest.futureTask2();
        callbackTest.callbackFuture();
    }
}
