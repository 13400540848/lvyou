package org.ume.school.modules.user.invite.reward;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.UserInviteReward;
import org.ume.school.modules.moneytype.MoneyTypeRepository;

import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * Created by Zz on 2018/9/3.
 */
@Service
public class UserInviteRewardService {

    @Resource
    private UserInviteRewardRepository userInviteRewardRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserRepository userRepository;
    
     @Transactional
     public UserInviteReward submit(UserInviteReward model) {
         return userInviteRewardRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(UserInviteReward project) {
         userInviteRewardRepository.delete(project);
     }

    public List<UserInviteReward> findByReferrerId(String userId) {
        return userInviteRewardRepository.findByReferrerId(userId);
    }

    public UserInviteReward findById(String id) {
        return userInviteRewardRepository.findOne(id);
    }
    
    public UserInviteReward findByUserIdAndReferrerId(String userId, String referrerId) {
        List<UserInviteReward> list = userInviteRewardRepository.findByUserIdAndReferrerId(userId, referrerId);
        return list!=null &&!list.isEmpty() ? list.get(0) : null;
    }
    
    public List<UserInviteReward> parse(List<UserInviteReward> list){
        if(!list.isEmpty()){
            for(UserInviteReward item : list){
                MoneyType t = moneyTypeRepository.findOne(item.getTypeId());
                if(t!=null){
                    item.setTypeName(t.getTypeName());
                }
//                UserEntity u = userRepository.findOne(item.getUserId());
//                if(u!=null){
//                    item.setUserName(u.getUserName());
//                }
            }
        }
        return list;
    }
    
    public UserInviteReward parse(UserInviteReward model){
        if(model!=null){
            MoneyType t = moneyTypeRepository.findOne(model.getTypeId());
            if(t!=null){
                model.setTypeName(t.getTypeName());
            }
        }
        return model;
    }
}
