package org.ume.school.modules.user.play.three;

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
import org.ume.school.modules.model.entity.Project;
import org.ume.school.modules.model.entity.UserPlayThree;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.user.play.three.info.UserPlayThreeInfoRepository;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * Created by Zz on 2018/11/1.
 */
@Service("userPlayThreeService")
public class UserPlayThreeService {

    @Resource
    private UserPlayThreeRepository userPlayThreeRepository;
    @Resource
    private UserPlayThreeInfoRepository userPlayThreeInfoRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserRepository userRepository;
    
     @Transactional
     public UserPlayThree submit(UserPlayThree model) {
         return userPlayThreeRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(UserPlayThree model) {
         userPlayThreeRepository.delete(model);
     }
     
     public List<UserPlayThree> findByPlayTime(Double playTime) {
         return userPlayThreeRepository.findByPlayTime(playTime);
     }
     
     public List<UserPlayThree> findByOrderId(String orderId) {
         return userPlayThreeRepository.findByOrderId(orderId);
     }
     
     
     
     /**
      * 查询并分页
      */
     @SuppressWarnings("unchecked")
     public Page<UserPlayThree> findAll(final UserPlayThree condition, String offset, String limit) {
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
         return userPlayThreeRepository.findAll(specification, pr);
     }
     
     public UserPlayThree get(String id) {
         return userPlayThreeRepository.findOne(id);
     }
     
     public List<UserPlayThree> findByUserId(String userId) {
         List<UserPlayThree> list = userPlayThreeRepository.findByUserIdOrderByCreateTimeDesc(userId);
         return parse(list);
     }
     
     public List<UserPlayThree> parse(List<UserPlayThree> list){
         if(!list.isEmpty()){
             for(UserPlayThree item : list){
//                 UserEntity u = userRepository.findOne(item.getUserId());
//                 if(u!=null){
//                     item.setUserName(u.getUserName());
//                 }
             }
         }
         return list;
     }
}
