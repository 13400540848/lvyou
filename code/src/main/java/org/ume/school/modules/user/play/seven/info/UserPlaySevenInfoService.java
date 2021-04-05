package org.ume.school.modules.user.play.seven.info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.UserPlaySevenInfo;

/**
 * Created by Zz on 2018/10/27.
 */
@Service("userPlaySevenInfoService")
public class UserPlaySevenInfoService {

    @Resource
    private UserPlaySevenInfoRepository userPlaySevenInfoRepository;
    
     @Transactional
     public UserPlaySevenInfo submit(UserPlaySevenInfo model) {
         return userPlaySevenInfoRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(UserPlaySevenInfo model) {
         userPlaySevenInfoRepository.delete(model);
     }
     
     public List<UserPlaySevenInfo> findByOrderId(String orderId) {
         return userPlaySevenInfoRepository.findByOrderId(orderId);
     }
     
     public List<UserPlaySevenInfo> findByOrderIdAndStatus(String orderId, Integer status) {
         return userPlaySevenInfoRepository.findByOrderIdAndStatus(orderId, status);
     }
     
     
     
     public List<UserPlaySevenInfo> findByPlayTime(Double playTime) {
         return userPlaySevenInfoRepository.findByPlayTime(playTime);
     }
 }