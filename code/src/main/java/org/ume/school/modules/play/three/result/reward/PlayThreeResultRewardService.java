package org.ume.school.modules.play.three.result.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.PlayThreeResultReward;

/**
 * Created by Zz on 2018/11/1.
 */
@Service("playThreeResultRewardService")
public class PlayThreeResultRewardService {

    @Resource
    private PlayThreeResultRewardRepository playThreeResultRewardRepository;

    @Transactional
    public PlayThreeResultReward submit(PlayThreeResultReward model) {
        return playThreeResultRewardRepository.saveAndFlush(model);
    }

    public PlayThreeResultReward get(String id) {
        return playThreeResultRewardRepository.findOne(id);
    }
    
    public List<PlayThreeResultReward> findByPlayTimeOrderByRewardCodeAsc(Double playTime) {
        return playThreeResultRewardRepository.findByPlayTimeOrderByRewardCodeAsc(playTime);
    }
}
