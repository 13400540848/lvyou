package org.ume.school.modules.play.seven.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlaySevenReward;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/play/seven/reward")
public class PlaySevenRewardController {

    @Resource
    private PlaySevenRewardService playSevenRewardService;
    
    /**
     * 获取配置列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlaySevenReward>> getList() {
        Response<List<PlaySevenReward>> result = new Response<List<PlaySevenReward>>();
        List<PlaySevenReward> list = playSevenRewardService.findAll();
        result.setRows(list);
        result.setTotal(list!=null ?  list.size() : 0);
        return result;
    }
    
    /**
     * 获取配置
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlaySevenReward> get(@PathVariable("id") String id) {
        Response<PlaySevenReward> result = new Response<PlaySevenReward>();
        PlaySevenReward model = playSevenRewardService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
}
