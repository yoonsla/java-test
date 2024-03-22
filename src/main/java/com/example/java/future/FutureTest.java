package com.example.java.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class FutureTest {

    /**
     * 2초 정지 후 Hello, Exit 순차적 출력
     */
    public void executor() throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        Thread.sleep(2000);
        log.info("Hello");
        log.info("Exit");
    }

    /**
     * execute
     *
     * runnable 인터페이스 필요 result값 설정 가능 runnable
     * 객체를 리턴하지 않음 exception 발생시키지 않음
     * 객체를 리턴할 필요가 없을 때 사용
     * shutdown() 걸어줘야 함
     *
     * result = Exit 우선 출력 / 2초 후 Hello
     */
    public void execute() {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Hello");
        });
        log.info("Exit");
    }

    /**
     * submit
     *
     * runnable, callable 인터페이스 사용 가능
     * runnable - 객체를 리턴하지 않음, exception 발생시키지 않음
     * callable - 값 리턴 가능, exception 발생시킬 수 있음
     * 객체 리턴이 필요하거나 exception 발생이 필요할 때 사용
     *
     * result = Exit -> Async
     */
    public void submit() {
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "Hello";
        });
        log.info("Exit");
    }

    /**
     * future
     *
     * 비동기적 연산, 작업을 수행한 후 도출된 결과를 나타내는 것
     * 타 thread에서 return한 값을 메인Thread에서 받고 싶을 때 사용
     *
     * result = false -> Async -> Exit > true -> Hello
     */
    public void future() throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newCachedThreadPool();
        Future<String> future = es.submit(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "Hello";
        });

        log.info(future.isDone());
        Thread.sleep(2100);
        log.info("Exit");
        log.info(future.isDone());
        log.info(future.get());
    }

    /**
     * futureTask
     *
     * Future 자체를 Object로 만들어줌
     *
     * result = false -> Async -> Exit > true -> Hello
     * es.execute(future)를 하지 않을 경우 Async / Hello 출력 안됨
     */
    public void futureTask() throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newCachedThreadPool();
        FutureTask<String> future = new FutureTask<>(()-> {
            Thread.sleep(2000);
            log.info("Async");
            return "Hello";
        });
        es.execute(future);

        log.info(future.isDone());
        Thread.sleep(2100);
        log.info("Exit");
        log.info((future.isDone()));
        log.info((future.get()));
    }

    /**
     * futureTask2
     *
     * done() 메소드 포함
     *
     * result = Async -> Hello -> Exit
     */
    public void futureTask2() throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        FutureTask<String> future = new FutureTask<>(() -> {
            Thread.sleep(2000);
            log.info("Async");
            return "Hello";
        }) { // 비동기 작업이 모두 완료되면 호출되는 hook같은 것.
            @Override
            protected void done() {
                try {
                    log.info(get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        es.execute(future);
        Thread.sleep(2100);
        log.info("Exit");
        es.shutdown(); // 이 메서드를 쓰더라도 하던 작업이 중단되지는 않음
    }

    /**
     * CallbackTask Future
     *
     * Callback을 이용하여 비동기 실행 결과를 처리할 수 있는 코드
     *
     */
    public void callback() {

    }
}
