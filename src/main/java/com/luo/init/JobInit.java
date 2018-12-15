package com.luo.init;

import com.luo.job.Job1;
import com.luo.job.Job2;
import com.luo.serviceImpl.JobServiceImpl;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
@Order(1)
public class JobInit implements ApplicationRunner {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobServiceImpl jobService;

    private static Logger logger = LoggerFactory.getLogger(JobInit.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Job Init");
        JobKey jobKey = new JobKey("job1","job");
        JobKey jobKey2 = new JobKey("job2","job2");
        // 构建job信息
        JobDetail jobDetail = jobService.geJobDetail(jobKey,"Job1",new JobDataMap(),Job1.class);
        // 按新的cronExpression表达式构建一个新的trigger
        Trigger trigger = jobService.getTrigger(jobKey,"0/10 * * * * ?");

        // 构建job信息
        JobDetail jobDetail2 = jobService.geJobDetail(jobKey2,"Job2",new JobDataMap(),Job2.class);
        // 按新的cronExpression表达式构建一个新的trigger
        Trigger trigger2 = jobService.getTrigger(jobKey2,"0/5 * * * * ?");
        try {

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.scheduleJob(jobDetail2, trigger2);
            scheduler.pauseAll();
            logger.info("创建定时任务成功");

        } catch (SchedulerException e) {
            logger.error("创建定时任务失败" , e);
        }
    }
}
