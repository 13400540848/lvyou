package org.ume.school.modules.play.three.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.PlayThreeReward;

/**
 * Created by Zz on 2018/11/1.
 */
@Service("playThreeRewardService")
public class PlayThreeRewardService {

    @Resource
    private PlayThreeRewardRepository playThreeRewardRepository;
    
     @Transactional
     public PlayThreeReward submit(PlayThreeReward model) {
         return playThreeRewardRepository.saveAndFlush(model);
     }
     
     public PlayThreeReward get(String id) {
         return playThreeRewardRepository.findOne(id);
     }
     
     public List<PlayThreeReward> findAll() {
         return playThreeRewardRepository.findByOrderByRewardCodeAsc();
     }
     
     public void delete(String id) {
         playThreeRewardRepository.delete(id);
     }
}
