package com.luo.controller;


import com.luo.job.Job1;
import com.luo.job.Job2;
import com.luo.job.LoadJob;
import com.luo.serviceImpl.JobServiceImpl;
import com.luo.util.SpringUtil;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Set;

@Controller
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobServiceImpl jobService;

    /**
     * 开启所有任务
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "startAllJob")
    @ResponseBody
    public String startAllJob() throws SchedulerException {
        scheduler.resumeAll();
        return "succ";
    }
    /**
     * 暂停某个任务
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "pauseJob")
    @ResponseBody
    public String pauseJob(String name,String group){
        try {
            scheduler.pauseJob(new JobKey(name,group));
            return "succ";
        } catch (SchedulerException e) {
            return "err";
        }
    }

    /**
     * 继续某个暂停任务
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "startJob")
    @ResponseBody
    public String startJob(String name,String group){
        try {
            scheduler.resumeJob(new JobKey(name,group));
            return "succ";
        } catch (SchedulerException e) {
            return "err";
        }
    }

    /**
     * 继续某个暂停任务
     * @return
     * @throws SchedulerException
     */
    @RequestMapping(value = "testThreadPool")
    @ResponseBody
    public void testThreadPool(String name,String group) throws SchedulerException {
//        System.out.println("------- 初始化完成 -----------");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
//        for (int count = 1; count <= 100; ++count) {
//            JobKey jobKey = new JobKey("job"+count,"job"+count);
//            JobDetail jobDetail = jobService.geJobDetail(jobKey,"",new JobDataMap(),LoadJob.class);
//            Trigger trigger = jobService.getTrigger(jobKey,"0 */1 * * * ?");
//            scheduler.scheduleJob(jobDetail,trigger);
//        }
//        scheduler.start();
    }
}
