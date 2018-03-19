package com.example.demo.Controllers;

import com.example.demo.domain.AThing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/asyncdemo")
@Slf4j
public class AsyncConntroller {
    private static final Timer timer = new Timer();
    private static final int sleepMillis = 1000;

    @RequestMapping("/sync")
    public AThing GetValueSync() throws InterruptedException {
        Thread.sleep(sleepMillis);
        return AThing.builder().value("hello").build();
    }

    @RequestMapping("/async")
    public CompletableFuture<AThing> GetValueAsync() {
        CompletableFuture<AThing> future = new CompletableFuture<>();
        TimerTask task = new TimerTask() {
            public void run() {
                future.complete(AThing.builder().value("hello").build());
            }
        };
        timer.schedule(task, sleepMillis);
        return future;
    }

    @RequestMapping("/badasync")
    public AThing GetValueAsyncBad() throws ExecutionException, InterruptedException {
        CompletableFuture<AThing> future = new CompletableFuture<>();
        TimerTask task = new TimerTask() {
            public void run() {
                future.complete(AThing.builder().value("hello").build());
            }
        };
        timer.schedule(task, sleepMillis);
        return future.get();
    }
}
