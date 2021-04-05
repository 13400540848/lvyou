package org.ume.school.modules.play.three.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlayThreeReward;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/play/three/reward")
public class PlayThreeRewardController {

    @Resource
    private PlayThreeRewardService playThreeRewardService;
    
    /**
     * 获取配置列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<PlayThreeReward>> getList() {
        Response<List<PlayThreeReward>> result = new Response<List<PlayThreeReward>>();
        List<PlayThreeReward> list = playThreeRewardService.findAll();
        result.setRows(list);
        result.setTotal(list!=null ?  list.size() : 0);
        return result;
    }
    
    /**
     * 获取配置
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlayThreeReward> get(@PathVariable("id") String id) {
        Response<PlayThreeReward> result = new Response<PlayThreeReward>();
        PlayThreeReward model = playThreeRewardService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
}
