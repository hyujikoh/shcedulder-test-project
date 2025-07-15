package com.scheduler.schedulerproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author hyunjikoh
 * @since 2025. 7. 6.
 */
@Configuration
@EnableScheduling
@Profile("multi")
public class SchedulerMultiThreadConfig {
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4);
        scheduler.setThreadNamePrefix("scheduler-multi-");
        scheduler.initialize();
        return scheduler;
    }
}
