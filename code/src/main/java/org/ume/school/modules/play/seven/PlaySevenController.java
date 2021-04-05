package org.ume.school.modules.play.seven;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlaySeven;
import org.ume.school.modules.model.entity.PlayThreeResult;
import org.ume.school.modules.play.three.result.PlayThreeResultService;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/play/seven")
public class PlaySevenController {

    @Resource
    private PlaySevenService playSevenService;
    @Resource
    private PlayThreeResultService playThreeResultService;
    
    /**
     * 获取配置
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<PlaySeven> get() {
        Response<PlaySeven> result = new Response<PlaySeven>();
        PlaySeven model = playSevenService.get();
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
