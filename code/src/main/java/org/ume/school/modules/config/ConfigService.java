package org.ume.school.modules.config;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.Constant;
import org.ume.school.modules.model.entity.Config;

/**
 * Created by Zz on 2018/8/30.
 */
@Service
public class ConfigService {

    @Resource
    private ConfigRepository configRepository;

     @Transactional
     public Config save(Config model) {
         return configRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(Config model) {
         configRepository.delete(model);
     }
     
    public List<Config> findList() {
        return configRepository.findAll();
    }

    public Config findById(String id) {
        return configRepository.findOne(id);
    }
    
    public Config findByCode(String code) {
        List<Config> list = configRepository.findByCode(code);
        return list!=null && !list.isEmpty() ? list.get(0) : null;
    }
    
    public float getCashBrokeScale(){
        Config c = findByCode(Constant.CASH_BROKE_SCALE);
        if(c!=null)
            return Float.parseFloat(c.getValue());
        return 0;
    }
    
    public float getCashMoneyMin(){
        Config c = findByCode(Constant.CASH_MONEY_MIN);
        if(c!=null)
            return Float.parseFloat(c.getValue());
        return 1;
    }
    
    public float getInviteRewardDG(){
        Config c = findByCode(Constant.INVITE_REWARD_DG);
        if(c!=null)
            return Float.parseFloat(c.getValue());
        return 0;
    }
}
