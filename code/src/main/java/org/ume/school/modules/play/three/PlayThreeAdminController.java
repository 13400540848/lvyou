package org.ume.school.modules.play.three;

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
import org.ume.school.modules.model.entity.PlayThree;
import org.ume.school.modules.quartz.QuartzJobThree;
import org.ume.school.modules.utils.QuartzUtils;
import org.ume.school.modules.utils.play.BaseUtils;

import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.AuthValidate;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/admin/play/three")
public class PlayThreeAdminController {

    @Resource
    private PlayThreeService playThreeService;

    /**
     * 获取配置
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<PlayThree> get(@AdminLogined IUser user) {
        Response<PlayThree> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlayThree> result = new Response<PlayThree>();
        PlayThree model = playThreeService.get();
        result.setRows(model);
        result.setTotal(model != null ? 1 : 0);
        return result;
    }

    /**
     * 提交
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<PlayThree> submitPlayThree(@RequestBody PlayThree model, @AdminLogined IUser user) {
        Response<PlayThree> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlayThree> resp = new Response<>();
        PlayThree modelDb = playThreeService.get();
        if (modelDb == null) {// 新增
            model.setId(UUID.randomUUID().toString());
            model.setAllMoney((double) 0);
            model.setCreateTime(new Date());
            model.setModifyTime(new Date());
            resp.setRows(playThreeService.submit((model)));
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
            resp.setRows(playThreeService.submit(modelDb));
        }
        return resp;
    }

    /**
     * 启动服务
     * @return
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Response<PlayThree> startService(@AdminLogined IUser user) {
        Response<PlayThree> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<PlayThree> resp = new Response<>();
        PlayThree modelDb = playThreeService.get();
        if (modelDb == null) {
            resp.setResultCode(ErrorTag.PLAY_REWARD_NOT_EXISTS.getCode());
            resp.setResultMsg(ErrorTag.PLAY_REWARD_NOT_EXISTS.getMessage());
            return resp;
        }
//        0 30-59/10 9-23 * * ?   每天9:30开始10分钟一次
        Integer playRate = modelDb.getPlayRate() !=null ? modelDb.getPlayRate() : 10; //默认10分钟
        String startTime = modelDb.getStartTime() != null ? modelDb.getStartTime() : "09:30";//默认九点半开始
        String cron = BaseUtils.getCronMinute(playRate, startTime);
        SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
        Scheduler sche;
        try {
            sche = gSchedulerFactory.getScheduler();
            String job_name = "快3开奖任务";
            System.out.println("【系统启动】开始 " +cron);
            QuartzUtils.removeJob(sche, job_name);
            QuartzUtils.addJob(sche, job_name, QuartzJobThree.class,  cron);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resp;
    }
}
