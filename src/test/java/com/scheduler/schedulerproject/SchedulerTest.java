package com.scheduler.schedulerproject;

import com.scheduler.schedulerproject.scheduler.MyScheduler;
import com.scheduler.schedulerproject.service.SchedulerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

/**
 * @author hyunjikoh
 * @since 2025. 7. 6.
 */

@SpringBootTest
@TestPropertySource(properties = {
        "logging.level.com.scheduler.schedulerproject=DEBUG"
})
@ActiveProfiles("single") // 단일 스레드 스케줄러 테스트
public class SchedulerTest {

    // SpyBean 대신 실제 서비스를 주입받고 스파이로 감싸는 방식 사용
    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("30초 작업이 10초 동안 실행되지 않는지 검증 또한 이로인해 다른 태스크들이 실행되지 않는지 검증")
    void testScheduledTasksWith30sTask(){
        // 실제 서비스를 스파이로 감싸기
        SchedulerService spyService = Mockito.spy(schedulerService);

        // 원본 참조 저장
        SchedulerService originalService = schedulerService;

        try {
            // 테스트를 위해 스파이 서비스로 교체
            ReflectionTestUtils.setField(applicationContext.getBean(MyScheduler.class),
                    "schedulerService", spyService);

            // 10초 동안 스케줄러가 실행되는지 검증
            await().atMost(Duration.ofSeconds(11))
                    .untilAsserted(() -> {
                        // Task1은 0.1초마다 실행되므로 최소 1회 이상 실행되어야 함
                        verify(spyService, never()).executeTask4();

                        // Task2는 2초마다 실행되므로 최소 1회 이상 실행되어야 함
                        verify(spyService, never()).executeTask2();

                        // Task3는 3초마다 실행되므로 최소 1회 이상 실행되어야 함
                        verify(spyService, never()).executeTask3();

                        // Task1는 1초마다 실행되므로 최소 1회 이상 실행되어야 함
                        verify(spyService, never()).executeTask1();
                    });
        } finally {
            // 테스트 후 원래 서비스로 복원
            ReflectionTestUtils.setField(applicationContext.getBean(MyScheduler.class),
                    "schedulerService", originalService);
        }
    }

    @Test
    void testTaskExecutionDuration() {
        // 각 태스크의 실행 시간을 검증
        SchedulerService realService = new SchedulerService();

        long startTime = System.currentTimeMillis();
        realService.executeTask1();
        long duration = System.currentTimeMillis() - startTime;
        assert duration >= 1000 : "Task1 실행 시간이 1초 미만입니다: " + duration;

        startTime = System.currentTimeMillis();
        realService.executeTask2();
        duration = System.currentTimeMillis() - startTime;
        assert duration >= 2000 : "Task2 실행 시간이 2초 미만입니다: " + duration;

        startTime = System.currentTimeMillis();
        realService.executeTask3();
        duration = System.currentTimeMillis() - startTime;
        assert duration >= 3000 : "Task3 실행 시간이 3초 미만입니다: " + duration;
    }
}