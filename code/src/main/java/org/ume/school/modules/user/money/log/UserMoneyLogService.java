package org.ume.school.modules.user.money.log;

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
import org.ume.school.modules.model.entity.UserMoneyLog;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.project.ProjectRepository;
import org.ume.school.modules.utils.PageUtils;

/**
 * Created by Zz on 2018/9/12.
 */
@Service
public class UserMoneyLogService {

    @Resource
    private UserMoneyLogRepository userMoneyLogRepository;
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    
     @Transactional
     public UserMoneyLog submit(UserMoneyLog model) {
         return userMoneyLogRepository.saveAndFlush(model);
     }
     
     public List<UserMoneyLog> findByUserId(String userId) {
         return userMoneyLogRepository.findByUserId(userId);
     }
     
     /**
      * 查询并分页
      */
     @SuppressWarnings("unchecked")
     public Page<UserMoneyLog> findAll(final UserMoneyLog condition, String offset, String limit) {
         @SuppressWarnings("rawtypes")
         Specification specification = new Specification<Project>() {
             @Override
             public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                 List<Predicate> predicates = new ArrayList<>();
                 if (condition.getTypeId()!=null && !condition.getTypeId().isEmpty()) {
                     predicates.add(cb.equal(root.get("typeId"), condition.getTypeId()));
                 }
                 if (condition.getUserId()!=null&& !condition.getUserId().isEmpty()) {
                     predicates.add(cb.equal(root.get("userId"),  condition.getUserId()));
                 }
                 return cb.and(predicates.toArray(new Predicate[predicates.size()]));
             }
         };
         PageRequest pr = PageUtils.compositePage(offset, limit, "createTime", "DESC");
         return userMoneyLogRepository.findAll(specification, pr);
     }
     
     public List<UserMoneyLog> parse(List<UserMoneyLog> list){
         if(!list.isEmpty()){
             for(UserMoneyLog item : list){
                 MoneyType t = moneyTypeRepository.findOne(item.getTypeId());
                 if(t!=null){
                     item.setTypeName(t.getTypeName());
                 }
             }
         }
         return list;
     }
}
