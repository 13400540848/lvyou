package org.ume.school.modules.play.seven;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.PlaySeven;

/**
 * Created by Zz on 2018/10/25.
 */
@Service("playSevenService")
public class PlaySevenService {

    @Resource
    private PlaySevenRepository playSevenRepository;
    
     @Transactional
     public PlaySeven submit(PlaySeven model) {
         return playSevenRepository.saveAndFlush(model);
     }
     
     public PlaySeven get() {
         List<PlaySeven> list = playSevenRepository.findAll();
         return list!=null && list.size()>0 ? list.get(0) : null;
     }
     
     public List<PlaySeven> findAll(String userId) {
         return playSevenRepository.findAll();
     }
}
