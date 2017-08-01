package jop;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 郑杰民 on 2017/7/28.
 */
public class MySchedulerFactory extends StdSchedulerFactory{
    private static final Logger logger = LoggerFactory.getLogger(MySchedulerFactory.class);

    @Override
    public Logger getLog() {
        return logger;
    }

    static MySchedulerFactory factory = null;


    public MySchedulerFactory(){
    }

    public MySchedulerFactory getInstance(){
        if (factory == null){
            factory = new MySchedulerFactory();
        }
        return factory;
    }


    public void run(JobDetail job,Trigger trigger){
        try {
            if(factory == null){
                factory = new MySchedulerFactory();
            }
            Scheduler scheduler = factory.getScheduler();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
