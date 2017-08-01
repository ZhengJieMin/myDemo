package jop;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;

import java.io.File;

/**
 * Created by 郑杰民 on 2017/7/28.
 */
public class Main {
    public static void main(String[] args) {
/*        MyTriggerFactory myTriggerFactory = new MyTriggerFactory();
        MySchedulerFactory mySchedulerFactory = new MySchedulerFactory();
        MyJobFactory myJobFactory = new MyJobFactory();

        mySchedulerFactory.getInstance().run(myJobFactory.getInstance().createNewJob("demo",DemoJop.class)
                ,myTriggerFactory.createTrigger("demo", SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(2)
                        .withRepeatCount(9)));//实际上运行 n + 1 次*/
        MySchedulerFactory mySchedulerFactory = new MySchedulerFactory();
        try {
//            System.getProperty("org.quartz.properties");
            System.setProperty("org.quartz.properties",Main.class.getClass().getResource("/").getPath()+"quartz.properties");
            Scheduler scheduler = mySchedulerFactory.getInstance().getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
