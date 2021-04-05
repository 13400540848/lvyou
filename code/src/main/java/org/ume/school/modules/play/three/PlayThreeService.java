package org.ume.school.modules.play.three;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.PlayThree;

/**
 * Created by Zz on 2018/11/1.
 */
@Service("playThreeService")
public class PlayThreeService {

    @Resource
    private PlayThreeRepository playThreeRepository;
    
     @Transactional
     public PlayThree submit(PlayThree model) {
         return playThreeRepository.saveAndFlush(model);
     }
     
     public PlayThree get() {
         List<PlayThree> list = playThreeRepository.findAll();
         return list!=null && list.size()>0 ? list.get(0) : null;
     }
     
     public List<PlayThree> findAll(String userId) {
         return playThreeRepository.findAll();
     }
}
