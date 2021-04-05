package org.ume.school.modules.play.seven.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.PlaySevenReward;

/**
 * Created by Zz on 2018/10/25.
 */
@Service("playSevenRewardService")
public class PlaySevenRewardService {

    @Resource
    private PlaySevenRewardRepository playSevenRewardRepository;
    
     @Transactional
     public PlaySevenReward submit(PlaySevenReward model) {
         return playSevenRewardRepository.saveAndFlush(model);
     }
     
     public PlaySevenReward get(String id) {
         return playSevenRewardRepository.findOne(id);
     }
     
     public List<PlaySevenReward> findAll() {
         return playSevenRewardRepository.findByOrderByRewardCodeAsc();
     }
     
     public void delete(String id) {
         playSevenRewardRepository.delete(id);
     }
}
