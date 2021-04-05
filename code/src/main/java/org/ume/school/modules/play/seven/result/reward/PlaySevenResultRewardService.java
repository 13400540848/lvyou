package org.ume.school.modules.play.seven.result.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.PlaySevenResultReward;

/**
 * Created by Zz on 2018/10/25.
 */
@Service("playSevenResultRewardService")
public class PlaySevenResultRewardService {

    @Resource
    private PlaySevenResultRewardRepository playSevenResultRewardRepository;

    @Transactional
    public PlaySevenResultReward submit(PlaySevenResultReward model) {
        return playSevenResultRewardRepository.saveAndFlush(model);
    }

    public PlaySevenResultReward get(String id) {
        return playSevenResultRewardRepository.findOne(id);
    }
    
    public List<PlaySevenResultReward> findByPlayTimeOrderByRewardCodeAsc(Integer playTime) {
        return playSevenResultRewardRepository.findByPlayTimeOrderByRewardCodeAsc(playTime);
    }
}
