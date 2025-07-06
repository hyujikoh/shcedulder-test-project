package com.scheduler.schedulerproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchedulerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerProjectApplication.class, args);
    }

}
