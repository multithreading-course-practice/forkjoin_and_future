package org.example.stage3;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * https://www.freeformatter.com/cron-expression-generator-quartz.html для генерации cron-выражений
 */
@Log4j2
public class CronExample {
    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        scheduler.start();

        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", "group1")
                .build();

        Trigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("cronTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();

        Trigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "group1")
                .withSchedule(simpleSchedule()
                        .withIntervalInMilliseconds(100)
                        .withRepeatCount(10)
                )
                .forJob(job)
                .startNow()
                .build();



        scheduler.scheduleJob(job, cronTrigger);
        scheduler.scheduleJob(simpleTrigger);

    }
}

