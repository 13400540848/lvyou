package org.ume.school.modules.user.money.cash;

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
import org.ume.school.modules.model.entity.UserMoneyCash;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * Created by Zz on 2018/9/12.
 */
@Service
public class UserMoneyCashService {

    @Resource
    private UserMoneyCashRepository userMoneyCashRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserRepository userRepository;
    
    @Transactional
    public UserMoneyCash submit(UserMoneyCash model) {
        return userMoneyCashRepository.saveAndFlush(model);
    }
    
    @Transactional
    public void delete(UserMoneyCash model) {
        userMoneyCashRepository.delete(model);
    }
    
    public List<UserMoneyCash> findByUserIdAndCheckStatus(String userId, Integer checkStatus) {
        return userMoneyCashRepository.findByUserIdAndCheckStatus(userId, checkStatus);
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<UserMoneyCash> findAll(final UserMoneyCash condition, String offset, String limit) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (condition.getCheckStatus()!=null) {
                    predicates.add(cb.equal(root.get("checkStatus"), condition.getCheckStatus()));
                }
                if (condition.getUserId()!=null) {
                    predicates.add(cb.equal(root.get("userId"),  condition.getUserId()));
                }
                if (condition.getOrderId()!=null && !condition.getOrderId().isEmpty()) {
                    predicates.add(cb.equal(root.get("orderId"),  condition.getOrderId()));
                }                 
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(offset, limit, "", "");
        return userMoneyCashRepository.findAll(specification, pr);
    }
    
    public UserMoneyCash get(String id) {
        return userMoneyCashRepository.findOne(id);
    }
    
    public List<UserMoneyCash> findByUserId(String userId) {
        List<UserMoneyCash> list = userMoneyCashRepository.findByUserId(userId);
        return parse(list);
    }
    
    public List<UserMoneyCash> parse(List<UserMoneyCash> list){
        if(!list.isEmpty()){
            for(UserMoneyCash item : list){
                MoneyType t = moneyTypeRepository.findOne(item.getTypeId());
                if(t!=null){
                    item.setTypeName(t.getTypeName());
                }
//                UserEntity u = userRepository.findOne(item.getUserId());
//                if(u!=null){
//                    item.setUserName(u.getUserName());
//                    item.setRealName(u.getRealName());
//                }
            }
        }
        return list;
    }
}
