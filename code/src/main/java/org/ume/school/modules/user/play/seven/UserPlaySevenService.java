package org.ume.school.modules.user.play.seven;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.model.entity.UserPlaySeven;
import org.ume.school.modules.model.entity.UserPlaySevenInfo;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.user.play.seven.info.UserPlaySevenInfoRepository;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * Created by Zz on 2018/10/27.
 */
@Service("userPlaySevenService")
public class UserPlaySevenService {

    @Resource
    private UserPlaySevenRepository userPlaySevenRepository;
    @Resource
    private UserPlaySevenInfoRepository userPlaySevenInfoRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserRepository userRepository;
    
     @Transactional
     public UserPlaySeven submit(UserPlaySeven model) {
         model = userPlaySevenRepository.saveAndFlush(model);         
         UserPlaySevenInfo info = new UserPlaySevenInfo();
         info.setCreateTime(model.getCreateTime());
         info.setId(UUID.randomUUID().toString());
         info.setMoney(model.getSumMoney());
         info.setOneNumber(model.getOneNumber());
         info.setOrderId(model.getOrderId());
         info.setPlayTime(model.getPlayTime());
         info.setPublishTime(model.getPublishTime());
         info.setSixNumber(model.getSixNumber());
         info.setStatus(model.getStatus());
         info.setUserId(model.getUserId());
         userPlaySevenInfoRepository.saveAndFlush(info);
         return model;
     }
     
     @Transactional
     public void delete(UserPlaySeven model) {
         userPlaySevenRepository.delete(model);
     }
     
     public List<UserPlaySeven> findByPlayTime(Double playTime) {
         return userPlaySevenRepository.findByPlayTime(playTime);
     }
     
     public List<UserPlaySeven> findByOrderId(String orderId) {
         return userPlaySevenRepository.findByOrderId(orderId);
     }
     
     
     
     /**
      * 查询并分页
      */
     @SuppressWarnings("unchecked")
     public Page<UserPlaySeven> findAll(final UserPlaySeven condition, String offset, String limit) {
         @SuppressWarnings("rawtypes")
         Specification specification = new Specification<Project>() {
             @Override
             public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                 List<Predicate> predicates = new ArrayList<>();
                 if (condition.getStatus()!=null) {
                     predicates.add(cb.equal(root.get("status"), condition.getStatus()));
                 }
                 if (condition.getUserId()!=null && !condition.getUserId().isEmpty()) {
                     predicates.add(cb.equal(root.get("userId"), condition.getUserId()));
                 }
                 if (condition.getOrderId()!=null && !condition.getOrderId().isEmpty()) {
                     predicates.add(cb.equal(root.get("orderId"), condition.getOrderId()));
                 }
                 if (condition.getPlayTime()!=null) {
                     predicates.add(cb.equal(root.get("playTime"), condition.getPlayTime()));
                 }
                 return cb.and(predicates.toArray(new Predicate[predicates.size()]));
             }
         };
         PageRequest pr = PageUtils.compositePage(offset, limit, "createTime", "Desc");
         return userPlaySevenRepository.findAll(specification, pr);
     }
     
     public UserPlaySeven get(String id) {
         return userPlaySevenRepository.findOne(id);
     }
     
     public List<UserPlaySeven> findByUserId(String userId) {
         List<UserPlaySeven> list = userPlaySevenRepository.findByUserIdOrderByCreateTimeDesc(userId);
         return parse(list);
     }
     
     public List<UserPlaySeven> parse(List<UserPlaySeven> list){
         if(!list.isEmpty()){
             for(UserPlaySeven item : list){
//                 UserEntity u = userRepository.findOne(item.getUserId());
//                 if(u!=null){
//                     item.setUserName(u.getUserName());
//                 }
             }
         }
         return list;
     }
}
