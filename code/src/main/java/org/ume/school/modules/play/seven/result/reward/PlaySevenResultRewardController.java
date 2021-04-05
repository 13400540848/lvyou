package org.ume.school.modules.play.seven.result.reward;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.PlaySevenResultReward;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/10/25.
 */
@RestController
@RequestMapping("/v0.1/play/seven/result/reward")
public class PlaySevenResultRewardController {

    @Resource
    private PlaySevenResultRewardService playSevenResultRewardService;
    
//    /**
//     * 列表
//     * @return
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public Response<List<PlaySevenResultReward>> getList(
//            @RequestParam(value = "offset", required = true) String offset,
//            @RequestParam(value = "limit", required = true) String limit) {
//        Response<List<PlaySevenResultReward>> result = new Response<List<PlaySevenResultReward>>();
//        final PlaySevenResultReward query = new PlaySevenResultReward();
//        query.setStatus(PlayResultStatus.WAIT.getValue());
//        Page<PlaySevenResultReward> PlaySevenResultRewards = playSevenResultRewardService.findAll(query, offset, limit);
//        result.setTotal(PlaySevenResultRewards.getTotalElements());
//        return result;
//    }
    
    
    /**
     * 获取
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<PlaySevenResultReward> get(@PathVariable("id") String id) {
        Response<PlaySevenResultReward> result = new Response<PlaySevenResultReward>();
        PlaySevenResultReward model = playSevenResultRewardService.get(id);
        result.setRows(model);
        result.setTotal(model!=null ?  1 : 0);
        return result;
    }
}
