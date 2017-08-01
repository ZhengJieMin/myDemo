package jop;

import org.quartz.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务工厂
 * Created by 郑杰民 on 2017/7/28.
 */
public class MyJobFactory extends JobBuilder {
    MyJobFactory factory = null;
    Map<String,List<JobDetail>> jobMap = null;

    public MyJobFactory(){
        jobMap = new HashMap<>();
    }

    public MyJobFactory getInstance(){
        if (factory == null){
            factory = new MyJobFactory();
        }
        return factory;
    }

    public List<JobDetail> getJobList(String groud){
        return jobMap.get(groud);
    }

    public void addJob(JobDetail job){
        addJob(Scheduler.DEFAULT_GROUP,job);
    }

    public void addJob(String groud,JobDetail job){
        List<JobDetail> list = this.getJobList(groud);
        if(list == null){
            list = new ArrayList<>();
            jobMap.put(groud,list);
        }
        list.add(job);
    }

    public JobDetail createNewJob(String name, Class<? extends Job> jobClass){
        return createNewJob(name,Scheduler.DEFAULT_GROUP,jobClass);
    }

    public JobDetail createNewJob(String name, String group, Class<? extends Job> jobClass){
        JobDetail job = factory.newJob(jobClass)
                .withIdentity(name,group)
                .usingJobData(new JobDataMap(new HashMap<String,Object>()))
                .build();
        addJob(group,job);
        return job;
    }
}
