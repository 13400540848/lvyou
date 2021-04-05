package org.ume.school.modules.user.play.three.info;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.UserPlayThreeInfo;

/**
 * Created by Zz on 2018/11/1.
 */
@Service("userPlayThreeInfoService")
public class UserPlayThreeInfoService {

    @Resource
    private UserPlayThreeInfoRepository userPlayThreeInfoRepository;
    
     @Transactional
     public UserPlayThreeInfo submit(UserPlayThreeInfo model) {
         return userPlayThreeInfoRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(UserPlayThreeInfo model) {
         userPlayThreeInfoRepository.delete(model);
     }
     
     public List<UserPlayThreeInfo> findByOrderId(String orderId) {
         return userPlayThreeInfoRepository.findByOrderId(orderId);
     }
     
     public List<UserPlayThreeInfo> findByOrderIdAndStatus(String orderId, Integer status) {
         return userPlayThreeInfoRepository.findByOrderIdAndStatus(orderId, status);
     }
     
     
     
     public List<UserPlayThreeInfo> findByPlayTime(Double playTime) {
         return userPlayThreeInfoRepository.findByPlayTime(playTime);
     }
 }