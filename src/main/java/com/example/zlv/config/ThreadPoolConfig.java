package com.example.zlv.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configurable
public class ThreadPoolConfig implements AsyncConfigurer {

    @Value("${thread.core.size:20}")
    private int threadCoreSize;
    @Value("${thread.max.size:50}")
    private int threadMaxSize;
    @Value("${thread.queue.size:50}")
    private int threadCapacitySize;
    @Value("${thread.queue.size:50}")
    private long keepActiveTime;

    @Override
    public Executor getAsyncExecutor() {
        return testThreadPoolTaskExecutor() ;
    }

    @Bean(name = "testPoolExecutor")
    public ThreadPoolExecutor testPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadCoreSize, threadMaxSize, keepActiveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(threadCapacitySize));
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return threadPoolExecutor;
    }


    @Bean(name = "testThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor testThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadCoreSize);
        threadPoolTaskExecutor.setMaxPoolSize(threadMaxSize);
        threadPoolTaskExecutor.setQueueCapacity(threadCapacitySize);
        threadPoolTaskExecutor.setThreadNamePrefix("s_thread");
        threadPoolTaskExecutor.setKeepAliveSeconds(Long.valueOf(keepActiveTime).intValue());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
