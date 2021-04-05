package org.ume.school.modules.play.three;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlayThree;
import org.ume.school.modules.model.entity.PlayThreeResult;
import org.ume.school.modules.play.three.result.PlayThreeResultService;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/play/three")
public class PlayThreeController {

    @Resource
    private PlayThreeService playThreeService;
    @Resource
    private PlayThreeResultService playThreeResultService;
    
    /**
     * 获取配置
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<PlayThree> get() {
        Response<PlayThree> result = new Response<PlayThree>();
        PlayThree model = playThreeService.get();
        if(model!=null){
            PlayThreeResult todayLast = playThreeResultService.getTodayOpenLast();
            if(todayLast!=null){
                model.setTodayOpenCount(todayLast.getDayIndex());
            }
        }
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
}
