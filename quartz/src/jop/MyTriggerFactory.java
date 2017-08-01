package jop;

import org.quartz.*;

import java.util.Date;

/**
 * (假工厂)
 * Created by 郑杰民 on 2017/7/28.
 */
public class MyTriggerFactory {
    MyTriggerFactory factory = null;

    public MyTriggerFactory() {
    }

    /**
     *
     * @param jobName
     * @param jobGroud
     * @param startDate
     * @param scheduleBuilder
     *                         SimpleScheduleBuilder.simpleSchedule()
     *                           //每5s运行一次
     *                           .withIntervalInSeconds(5)
     *                            //重复运行3次
     *                           .withRepeatCount(3)
     * @return
     */
    public Trigger createTrigger(String jobName, String jobGroud, Date startDate,ScheduleBuilder scheduleBuilder){
        return TriggerBuilder.newTrigger()
                .forJob(jobName,jobGroud)
                .startAt(startDate)
                .withSchedule(scheduleBuilder)
                .build();
    }

    public Trigger createTrigger(String jobName, Date startDate,ScheduleBuilder scheduleBuilder){
        return createTrigger(jobName,Scheduler.DEFAULT_GROUP,startDate,scheduleBuilder);
    }

    public Trigger createTrigger(String jobName, String jobGroud,ScheduleBuilder scheduleBuilder){
        return TriggerBuilder.newTrigger()
                .forJob(jobName,jobGroud)
                .startNow()
                .withSchedule(scheduleBuilder)
                .build();
    }

    public Trigger createTrigger(String jobName,ScheduleBuilder scheduleBuilder){
        return createTrigger(jobName,Scheduler.DEFAULT_GROUP,scheduleBuilder);
    }
}
