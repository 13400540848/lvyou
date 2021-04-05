package org.ume.school.modules.user.money.project;

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
import org.ume.school.modules.model.entity.UserMoneyProject;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.project.ProjectRepository;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * Created by Zz on 2018/9/12.
 */
@Service
public class UserMoneyProjectService {

    @Resource
    private UserMoneyProjectRepository userMoneyProjectRepository;
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserRepository userRepository;
    
     @Transactional
     public UserMoneyProject submit(UserMoneyProject model) {
         return userMoneyProjectRepository.saveAndFlush(model);
     }
     
     @Transactional
     public void delete(UserMoneyProject model) {
         userMoneyProjectRepository.delete(model);
     }
     
     public List<UserMoneyProject> findByUserIdAndProjectIdAndStatus(String userId, String projectId, Integer status) {
         return userMoneyProjectRepository.findByUserIdAndProjectIdAndStatus(userId, projectId, status);
     }
     
     public List<UserMoneyProject> findByProjectIdAndStatusOrderByCreateTimeDESC(String projectId, Integer status) {
         return userMoneyProjectRepository.findByProjectIdAndStatusOrderByCreateTimeDesc(projectId, status);
     }
     
     
     /**
      * 查询并分页
      */
     @SuppressWarnings("unchecked")
     public Page<UserMoneyProject> findAll(final UserMoneyProject condition, String offset, String limit) {
         @SuppressWarnings("rawtypes")
         Specification specification = new Specification<Project>() {
             @Override
             public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                 List<Predicate> predicates = new ArrayList<>();
                 if (condition.getUserId()!=null && !condition.getUserId().isEmpty()) {
                     predicates.add(cb.equal(root.get("userId"),  condition.getUserId()));
                 }
                 if (condition.getProjectId()!=null && !condition.getProjectId().isEmpty()) {
                     predicates.add(cb.equal(root.get("projectId"),  condition.getProjectId()));
                 }
                 return cb.and(predicates.toArray(new Predicate[predicates.size()]));
             }
         };
         PageRequest pr = PageUtils.compositePage(offset, limit, "createTime", "Desc");
         return userMoneyProjectRepository.findAll(specification, pr);
     }
     
     public List<UserMoneyProject> findByUserId(String userId) {
         return userMoneyProjectRepository.findByUserId(userId);
     }
     
     public UserMoneyProject get(String id) {
         return userMoneyProjectRepository.findOne(id);
     }
     
     public Integer countByProjectId(String projectId) {
         return userMoneyProjectRepository.countByProjectId(projectId);
     }
     
     public List<UserMoneyProject> parse(List<UserMoneyProject> list){
         if(!list.isEmpty()){
             for(UserMoneyProject item : list){
                 Project p = projectRepository.findOne(item.getProjectId());
                 if(p!=null){
                     item.setProjectName(p.getTitle());
                     item.setProject(p);
                 }
                 MoneyType t = moneyTypeRepository.findOne(item.getTypeId());
                 if(t!=null){
                     item.setTypeName(t.getTypeName());
                 }
//                 UserEntity u = userRepository.findOne(item.getUserId());
//                 if(u!=null){
//                     item.setUserName(u.getUserName());
//                 }
             }
         }
         return list;
     }
}
