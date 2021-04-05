package org.ume.school.modules.play.three.result.reward;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlayThreeResultReward;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/11/1.
 */
@RestController
@RequestMapping("/v0.1/play/three/result/reward")
public class PlayThreeResultRewardController {

    @Resource
    private PlayThreeResultRewardService playThreeResultRewardService;
    
//    /**
//     * 列表
//     * @return
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public Response<List<PlayThreeResultReward>> getList(
//            @RequestParam(value = "offset", required = true) String offset,
//            @RequestParam(value = "limit", required = true) String limit) {
//        Response<List<PlayThreeResultReward>> result = new Response<List<PlayThreeResultReward>>();
//        final PlayThreeResultReward query = new PlayThreeResultReward();
//        query.setStatus(PlayResultStatus.WAIT.getValue());
//        Page<PlayThreeResultReward> PlayThreeResultRewards = playThreeResultRewardService.findAll(query, offset, limit);
//        result.setTotal(PlayThreeResultRewards.getTotalElements());
//        return result;
//    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlayThreeResultReward> get(@PathVariable("id") String id) {
        Response<PlayThreeResultReward> result = new Response<PlayThreeResultReward>();
        PlayThreeResultReward model = playThreeResultRewardService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
}
