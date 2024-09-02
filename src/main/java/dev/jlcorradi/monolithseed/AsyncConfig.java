package dev.jlcorradi.monolithseed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor(@Value("${async.corePoolSize:10}") int corePoolSize,
                                 @Value("${async.mazPoolSize:50}") int maxPoolSize,
                                 @Value("${async.queueCapacity:500}") int queueCapacity) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    @Primary
    @ConditionalOnProperty(value = "spring.threads.virtual.enabled", matchIfMissing = true)
    @Bean(name = "virtualThreadTaskExecutor")
    public Executor virtualThreadTaskExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
