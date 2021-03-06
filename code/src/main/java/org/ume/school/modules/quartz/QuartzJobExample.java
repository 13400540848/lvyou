package org.ume.school.modules.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * quartz示例定时器类
 * 
 * @author Administrator
 * 
 */
public class QuartzJobExample implements Job {
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	
//    	SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
//        Scheduler sche = gSchedulerFactory.getScheduler();
//        String job_name = "动态任务调度";
//        System.out.println("【系统启动】开始(每1秒输出一次)...");
//        QuartzManager.addJob(sche, job_name, QuartzJobExample.class, "0/1 * * * * ?");
//
//        Thread.sleep(3000);
//        System.out.println("【修改时间】开始(每2秒输出一次)...");
//        QuartzManager.modifyJobTime(sche, job_name, "10/2 * * * * ?");
//        Thread.sleep(4000);
//        System.out.println("【移除定时】开始...");
//        QuartzManager.removeJob(sche, job_name);
//        System.out.println("【移除定时】成功");
//
//        System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");
//        QuartzManager.addJob(sche, job_name, QuartzJobExample.class, "*/10 * * * * ?");
//        Thread.sleep(30000);
//        System.out.println("【移除定时】开始...");
//        QuartzManager.removeJob(sche, job_name);
//        System.out.println("【移除定时】成功");
        
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "★★★★★★★★★★★");
    }
}