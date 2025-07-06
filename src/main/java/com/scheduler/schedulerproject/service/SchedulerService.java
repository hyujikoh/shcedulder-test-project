package com.scheduler.schedulerproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author hyunjikoh
 * @since 2025. 7. 6.
 */
@Service
public class SchedulerService {
    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private final AtomicInteger scheduler1Count = new AtomicInteger(0);
    private final AtomicInteger scheduler2Count = new AtomicInteger(0);
    private final AtomicInteger scheduler3Count = new AtomicInteger(0);

    public void executeTask1() {
        int count = scheduler1Count.incrementAndGet();
        String startTime = LocalDateTime.now().format(formatter);
        String threadName = Thread.currentThread().getName();

        log.info("🔵 [TASK-1] 시작 - 실행횟수: {}, 시간: {}, 스레드: {}", count, startTime, threadName);

        try {
            // 1초 작업 시뮬레이션
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Task1 interrupted", e);
        }

        String endTime = LocalDateTime.now().format(formatter);
        log.info("🔵 [TASK-1] 완료 - 실행횟수: {}, 시간: {}, 스레드: {}", count, endTime, threadName);
    }

    public void executeTask2() {
        int count = scheduler2Count.incrementAndGet();
        String startTime = LocalDateTime.now().format(formatter);
        String threadName = Thread.currentThread().getName();

        log.info("🟢 [TASK-2] 시작 - 실행횟수: {}, 시간: {}, 스레드: {}", count, startTime, threadName);

        try {
            // 2초 작업 시뮬레이션
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Task2 interrupted", e);
        }

        String endTime = LocalDateTime.now().format(formatter);
        log.info("🟢 [TASK-2] 완료 - 실행횟수: {}, 시간: {}, 스레드: {}", count, endTime, threadName);
    }

    public void executeTask3() {
        int count = scheduler3Count.incrementAndGet();
        String startTime = LocalDateTime.now().format(formatter);
        String threadName = Thread.currentThread().getName();

        log.info("🟡 [TASK-3] 시작 - 실행횟수: {}, 시간: {}, 스레드: {}", count, startTime, threadName);

        try {
            // 3초 작업 시뮬레이션
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Task3 interrupted", e);
        }

        String endTime = LocalDateTime.now().format(formatter);
        log.info("🟡 [TASK-3] 완료 - 실행횟수: {}, 시간: {}, 스레드: {}", count, endTime, threadName);
    }

    public void printSummary() {
        log.info("📊 [요약] Task1: {}회, Task2: {}회, Task3: {}회",
                scheduler1Count.get(), scheduler2Count.get(), scheduler3Count.get());
    }
}
