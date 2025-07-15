package com.scheduler.schedulerproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hyunjikoh
 * @since 2025. 7. 6.
 */
@Configuration
@EnableScheduling
@Profile("single")
public class SchedulerConfig {
}
