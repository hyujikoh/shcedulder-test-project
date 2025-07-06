package com.scheduler.schedulerproject.scheduler;

import com.scheduler.schedulerproject.service.SchedulerService;
import jakarta.annotation.PostConstruct;
import org.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hyunjikoh
 * @since 2025. 7. 6.
 */
@Component
public class MyScheduler {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Autowired
    private SchedulerService schedulerService;

    private LocalDateTime startTime;

    @PostConstruct
    public void init() {
        startTime = LocalDateTime.now();
        Logger.info("🚀 [테스트 시작] 시간: {}", startTime.format(formatter));
        Logger.info("📋 [테스트 조건] 단일 스레드, 각 작업 1초/2초/3초, 총 10초간 테스트");

        // 10초 후 요약 출력
        new Thread(() -> {
            try {
                Thread.sleep(10000);
                schedulerService.printSummary();
                Logger.info("🏁 [테스트 종료] 시간: {}", LocalDateTime.now().format(formatter));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // 매 1초마다 실행 (1초 작업)
    @Scheduled(fixedRate = 1000)
    public void scheduler1() {
        if (isTestPeriod()) {
            schedulerService.executeTask1();
        }
    }

    // 매 2초마다 실행 (2초 작업)
    @Scheduled(fixedRate = 2000)
    public void scheduler2() {
        if (isTestPeriod()) {
            schedulerService.executeTask2();
        }
    }

    // 매 3초마다 실행 (3초 작업)
    @Scheduled(fixedRate = 3000)
    public void scheduler3() {
        if (isTestPeriod()) {
            schedulerService.executeTask3();
        }
    }

    private boolean isTestPeriod() {
        return LocalDateTime.now().isBefore(startTime.plusSeconds(10));
    }
}