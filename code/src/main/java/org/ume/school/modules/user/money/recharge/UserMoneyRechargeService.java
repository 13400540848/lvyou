package org.ume.school.modules.user.money.recharge;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.model.entity.MoneyType;
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.model.entity.UserMoneyRecharge;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * Created by Zz on 2018/9/12.
 */
@Service
public class UserMoneyRechargeService {

    @Resource
    private UserMoneyRechargeRepository userMoneyRechargeRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserRepository userRepository;
    
     @Transactional
     public UserMoneyRecharge submit(UserMoneyRecharge model) {
         return userMoneyRechargeRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(UserMoneyRecharge model) {
         userMoneyRechargeRepository.delete(model);
     }
     
     public List<UserMoneyRecharge> findByUserIdAndCheckStatus(String userId, Integer checkStatus) {
         return userMoneyRechargeRepository.findByUserIdAndCheckStatus(userId, checkStatus);
     }
     
     /**
      * 查询并分页
      */
     @SuppressWarnings("unchecked")
     public Page<UserMoneyRecharge> findAll(final UserMoneyRecharge condition, String offset, String limit) {
         @SuppressWarnings("rawtypes")
         Specification specification = new Specification<Project>() {
             @Override
             public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                 List<Predicate> predicates = new ArrayList<>();
                 if (condition.getCheckStatus()!=null) {
                     predicates.add(cb.equal(root.get("checkStatus"), condition.getCheckStatus()));
                 }
                 if (condition.getUserId()!=null && !condition.getUserId().isEmpty()) {
                     predicates.add(cb.equal(root.get("userId"),  condition.getUserId()));
                 }
                 if (condition.getOrderId()!=null && !condition.getOrderId().isEmpty()) {
                     predicates.add(cb.equal(root.get("orderId"),  condition.getOrderId()));
                 }                 
                 return cb.and(predicates.toArray(new Predicate[predicates.size()]));
             }
         };
         PageRequest pr = PageUtils.compositePage(offset, limit, "", "");
         return userMoneyRechargeRepository.findAll(specification, pr);
     }
     
     public UserMoneyRecharge get(String id) {
         return userMoneyRechargeRepository.findOne(id);
     }
     
     public List<UserMoneyRecharge> findByUserId(String userId) {
         List<UserMoneyRecharge> list = userMoneyRechargeRepository.findByUserIdOrderByCreateTimeDesc(userId);
         return parse(list);
     }
     
     public List<UserMoneyRecharge> parse(List<UserMoneyRecharge> list){
         if(!list.isEmpty()){
             for(UserMoneyRecharge item : list){
                 MoneyType t = moneyTypeRepository.findOne(item.getTypeId());
                 if(t!=null){
                     item.setTypeName(t.getTypeName());
                 }
//                 UserEntity u = userRepository.findOne(item.getUserId());
//                 if(u!=null){
//                     item.setUserName(u.getUserName());
//                     item.setRealName(u.getRealName());
//                 }
             }
         }
         return list;
     }
}
