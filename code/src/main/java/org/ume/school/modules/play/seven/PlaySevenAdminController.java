package org.ume.school.modules.play.seven;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.ErrorTag;
import org.ume.school.modules.model.entity.PlaySeven;
import org.ume.school.modules.quartz.QuartzJobSeven;
import org.ume.school.modules.utils.QuartzUtils;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/admin/play/seven")
public class PlaySevenAdminController {

    @Resource
    private PlaySevenService playSevenService;

    /**
     * 获取配置
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<PlaySeven> get(@AdminLogined IUser user) {
        Response<PlaySeven> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlaySeven> result = new Response<PlaySeven>();
        PlaySeven model = playSevenService.get();
        result.setRows(model);
        result.setTotal(model != null ? 1 : 0);
        return result;
    }

    /**
     * 提交
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<PlaySeven> submitPlaySeven(@RequestBody PlaySeven model, @AdminLogined IUser user) {
        Response<PlaySeven> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlaySeven> resp = new Response<>();
        PlaySeven modelDb = playSevenService.get();
        if (modelDb == null) {// 新增
            model.setId(UUID.randomUUID().toString());
            model.setAllMoney((double) 0);
            model.setCreateTime(new Date());
            model.setModifyTime(new Date());
            resp.setRows(playSevenService.submit((model)));
        }
        else {
            modelDb.setEndTime(model.getEndTime());
            modelDb.setPerMoney(model.getPerMoney());
            modelDb.setPlayName(model.getPlayName());
            modelDb.setPlayRate(model.getPlayRate());
            modelDb.setStartTime(model.getStartTime());
            modelDb.setCountTime(model.getCountTime());
            modelDb.setPlayRemark(model.getPlayRemark());            
            modelDb.setModifyTime(new Date());
            resp.setRows(playSevenService.submit(modelDb));
        }
        return resp;
    }

    /**
     * 启动服务
     * @return
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Response<PlaySeven> startService(@AdminLogined IUser user) {
        Response<PlaySeven> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlaySeven> resp = new Response<>();
        PlaySeven modelDb = playSevenService.get();
        if (modelDb == null) {
            resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
            resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
            return resp;
        }
        // "0 15 10 ? * *" 每天上午10:15触发
        //0 0 17 ? * TUES,THUR,SAT 每周二、四、六下午五点 
//        String playRate = modelDb.getPlayRate();
//        playRate = playRate.isEmpty() ? "2,4,6":playRate;
//        playRate = BaseUtils.getCronWeek(playRate);
//        String publishTime = modelDb.getPublishTime();
//        publishTime = publishTime.isEmpty() ? "0 0 21" : BaseUtils.getCronTime(publishTime);
//        String cron = publishTime + " ? * " + playRate;
        
        String cron =  "0 */1 * * * ?"; //每分钟执行一次
        SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
        Scheduler sche;
        try {
            sche = gSchedulerFactory.getScheduler();
            String job_name = "双色球开奖任务";
            System.out.println("【系统启动】开始 " +cron);
            QuartzUtils.removeJob(sche, job_name);
            QuartzUtils.addJob(sche, job_name, QuartzJobSeven.class,  cron);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resp;
    }
}
