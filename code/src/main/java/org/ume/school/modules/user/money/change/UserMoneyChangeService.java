package org.ume.school.modules.user.money.change;

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
import org.ume.school.modules.model.entity.UserMoneyChange;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.utils.PageUtils;

/**
 * Created by Zz on 2018/10/15.
 */
@Service
public class UserMoneyChangeService {

    @Resource
    private UserMoneyChangeRepository userMoneyChangeRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    
     @Transactional
     public UserMoneyChange submit(UserMoneyChange model) {
         return userMoneyChangeRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(UserMoneyChange model) {
         userMoneyChangeRepository.delete(model);
     }
     
     /**
      * 查询并分页
      */
     @SuppressWarnings("unchecked")
     public Page<UserMoneyChange> findAll(final UserMoneyChange condition, String offset, String limit) {
         @SuppressWarnings("rawtypes")
         Specification specification = new Specification<UserMoneyChange>() {
             @Override
             public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                 List<Predicate> predicates = new ArrayList<>();
                 if (condition.getUserId()!=null && !condition.getUserId().isEmpty()) {
                     predicates.add(cb.equal(root.get("userId"),  condition.getUserId()));
                 }
                 return cb.and(predicates.toArray(new Predicate[predicates.size()]));
             }
         };
         PageRequest pr = PageUtils.compositePage(offset, limit, "createTime", "Desc");
         return userMoneyChangeRepository.findAll(specification, pr);
     }
     
     public UserMoneyChange get(String id) {
         return userMoneyChangeRepository.findOne(id);
     }
     
     public List<UserMoneyChange> findByUserId(String userId) {
         List<UserMoneyChange> list = userMoneyChangeRepository.findByUserIdOrderByCreateTimeDesc(userId);
         return parse(list);
     }
     
     public List<UserMoneyChange> parse(List<UserMoneyChange> list){
         if(!list.isEmpty()){
             for(UserMoneyChange item : list){
                 MoneyType t = moneyTypeRepository.findOne(item.getFromTypeId());
                 if(t!=null){
                     item.setFromTypeName(t.getTypeName());
                 }
                 MoneyType t1 = moneyTypeRepository.findOne(item.getToTypeId());
                 if(t1!=null){
                     item.setToTypeName(t1.getTypeName());
                 }
             }
         }
         return list;
     }
}
