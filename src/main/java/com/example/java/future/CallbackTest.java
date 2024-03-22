package com.example.java.future;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CallbackTest {

    interface SuccessCallback {

        void onSuccess(String result);
    }

    interface ExceptionalCallback {

        void onError(Throwable throwable);
    }

    public static class CallbackFutureTask extends FutureTask<String> {

        SuccessCallback successCallback;
        ExceptionalCallback exceptionalCallback;

        public CallbackFutureTask(Callable<String> callable, SuccessCallback successCallback, ExceptionalCallback exceptionalCallback) {
            super(callable);
            this.successCallback = Objects.requireNonNull(successCallback);
            this.exceptionalCallback = Objects.requireNonNull(exceptionalCallback);
        }

        @Override
        protected void done() {
            try {
                successCallback.onSuccess(get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                exceptionalCallback.onError(e.getCause());
            }
        }
    }

    public void callbackFuture() {
        ExecutorService es = Executors.newCachedThreadPool();
        CallbackFutureTask future = new CallbackFutureTask(() -> {
            Thread.sleep(2000);
//            if(1 == 1) throw new RuntimeException("Async ERROR!!!");
            log.info("Async");
            return "Hello";
        },
            log::info,
            e -> log.info("Error ==> {}", e.getMessage()));

        es.execute(future);
        es.shutdown();
    }
}
