package com.example.zlv.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestService {


    @Async("testPoolExecutor")
    public void testAsync() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Async("testThreadPoolTaskExecutor")
    public void tesStAsync() throws InterruptedException {
        Thread.sleep(1000);
    }
}
