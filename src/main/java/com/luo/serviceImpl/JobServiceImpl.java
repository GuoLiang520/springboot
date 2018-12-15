package com.luo.serviceImpl;

import org.quartz.*;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl {
    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail geJobDetail(JobKey jobKey, String description, JobDataMap map,Class c) {
        return JobBuilder.newJob(c)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }
    //获取Trigger (Job的触发器,执行规则)
    public Trigger getTrigger(JobKey jobKey,String cron) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }
    //获取JobKey,包含Name和Group
    public JobKey getJobKey(String name,String group) {
        return JobKey.jobKey(name, group);
    }
}
