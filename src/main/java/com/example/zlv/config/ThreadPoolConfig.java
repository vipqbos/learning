package com.example.zlv.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configurable
public class ThreadPoolConfig {

    @Value("${thread.core.size:20}")
    private int threadCoreSize;
    @Value("${thread.max.size:50}")
    private int threadMaxSize;
    @Value("${thread.queue.size:50}")
    private int threadCapacitySize;
    @Value("${thread.queue.size:50}")
    private long keepActiveTime;


    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(threadCoreSize,threadMaxSize,keepActiveTime, TimeUnit.SECONDS,new ArrayBlockingQueue<>(threadCapacitySize));
    }


    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadCoreSize);
        threadPoolTaskExecutor.setMaxPoolSize(threadMaxSize);
        threadPoolTaskExecutor.setQueueCapacity(threadCapacitySize);
        threadPoolTaskExecutor.setKeepAliveSeconds(Long.valueOf(keepActiveTime).intValue());
        return threadPoolTaskExecutor;
    }
}
