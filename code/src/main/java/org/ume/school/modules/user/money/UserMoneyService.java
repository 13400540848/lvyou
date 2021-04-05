package org.ume.school.modules.user.money;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.UserMoney;
import org.ume.school.modules.model.entity.UserMoneyLog;
import org.ume.school.modules.model.enums.MoneyLogType;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.user.money.log.UserMoneyLogRepository;

import com.bluesimon.wbf.IUser;

/**
 * Created by Zz on 2018/9/3.
 */
@Service("userMoneyService")
public class UserMoneyService {

    @Resource
    private UserMoneyRepository userMoneyRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserMoneyLogRepository userMoneyLogRepository;
    
     @Transactional
     public UserMoney submit(UserMoney model) {
         return userMoneyRepository.saveAndFlush(model);
     }
     
     @Transactional
     public UserMoney save(String userId, String typeId, double changeMoney, MoneyLogType mlt, IUser adminUser) {
         //用户钱包
         UserMoney um = findByUserIdAndTypeId(userId, typeId);
         
         //增加用户交易记录
         UserMoneyLog uml = new UserMoneyLog();
         uml.setAfterMoney(um.getMoney() + changeMoney);
         uml.setBeforeMoney(um.getMoney());
         uml.setCreateTime(new Date());
         uml.setId(UUID.randomUUID().toString());
         uml.setLogType(mlt.getValue());
         uml.setMoney(changeMoney);
         uml.setTypeId(typeId);
         uml.setUserId(userId);
//         if(adminUser!=null){
//             uml.setAdminId(adminUser.getId());
//             uml.setAdminName(adminUser.getUserName());
//         }
         userMoneyLogRepository.save(uml);
         
         //增加钱包金额
         um.setMoney(um.getMoney() + changeMoney);
         
         return userMoneyRepository.saveAndFlush(um);
     }
     
     @Transactional
     public void delete(UserMoney project) {
         userMoneyRepository.delete(project);
     }
     
     public UserMoney findByUserIdAndTypeId(String userId, String typeId) {
         List<UserMoney> list = userMoneyRepository.findByUserIdAndTypeId(userId, typeId);
         if(list.isEmpty()){
             UserMoney um = new UserMoney();
             um.setUserId(userId);
             um.setTypeId(typeId);
             um.setId(UUID.randomUUID().toString());
             um.setMoney((double)0);
             um = userMoneyRepository.saveAndFlush(um);
             return um;
         }
         return list.get(0);
     }

    public List<UserMoney> findByUserId(String userId) {
        return userMoneyRepository.findByUserId(userId);
    }

    public UserMoney findById(String id) {
        return userMoneyRepository.findOne(id);
    }
    
    public List<UserMoney> parse(List<UserMoney> list){
        if(list != null && !list.isEmpty()){
            for(UserMoney item : list){
                item = parse(item);
            }
        }
        return list;
    }
    
    public UserMoney parse(UserMoney model){
        if(model!=null){
            MoneyType mt = moneyTypeRepository.findOne(model.getTypeId());
            if(mt!=null){
                model.setTypeName(mt.getTypeName());
                model.setMoneyType(mt);
            }
        }
        return model;
    }
}
