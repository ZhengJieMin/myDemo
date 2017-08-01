package jop;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by 郑杰民 on 2017/7/21.
 */
public class DemoJop implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Object index = jobExecutionContext.get("index");
        if(index == null){
            index = (Integer)1;
        }
        System.out.println(jobExecutionContext.getJobDetail().getDescription()+"->"+Thread.currentThread().getId()+":"+Thread.currentThread().getName()+":"+index);
        jobExecutionContext.put("index", (Integer)((int)index+1));
    }
}
