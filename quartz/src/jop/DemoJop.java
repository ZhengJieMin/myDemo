package jop;

import org.quartz.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 郑杰民 on 2017/7/21.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DemoJop implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap  dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        synchronized (dataMap) {
            if (dataMap.get("count") == null) {
                dataMap.put("count", 1);
            } else {
                dataMap.put("count", (int) dataMap.get("count") + 1);
            }
        }

        int index =  (int)dataMap.get("count");
        System.out.println(jobExecutionContext.getJobDetail().getDescription()+"->"+Thread.currentThread().getId()+":"+Thread.currentThread().getName()+":"+index);
    }
}
