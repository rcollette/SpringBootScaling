package com.example.demo.Controllers;

import com.example.demo.domain.AThing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/asyncdemo")
@Slf4j
public class AsyncConntroller {

    public static final int sleepMillis = 1000;

    @RequestMapping("/sync")
    public AThing GetValueSync() throws InterruptedException {
        Thread.sleep(sleepMillis);
        return AThing.builder().value("hello").build();
    }

    @RequestMapping("/async")
    public CompletableFuture<AThing> GetValueAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return AThing.builder().value("hello").build();
        });
    }

    @RequestMapping("/badasync")
    public AThing GetValueAsyncBad() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return AThing.builder().value("hello").build();
        }).get();
    }
}
