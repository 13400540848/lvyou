package org.ume.school.modules.user.money.buy;

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
import org.ume.school.modules.model.entity.UserMoneyBuy;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * Created by Zz on 2018/9/12.
 */
@Service
public class UserMoneyBuyService {

    @Resource
    private UserMoneyBuyRepository userMoneyBuyRepository;
    @Resource
    private MoneyTypeRepository moneyTypeRepository;
    @Resource
    private UserRepository userRepository;
    
    @Transactional
    public UserMoneyBuy submit(UserMoneyBuy model) {
        return userMoneyBuyRepository.saveAndFlush(model);
    }
    
    @Transactional
    public void delete(UserMoneyBuy model) {
        userMoneyBuyRepository.delete(model);
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<UserMoneyBuy> findAll(final UserMoneyBuy condition, String offset, String limit) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (condition.getSellId()!=null) {
                    predicates.add(cb.equal(root.get("sellId"), condition.getSellId()));
                }
                if (condition.getUserId()!=null) {
                    predicates.add(cb.equal(root.get("userId"),  condition.getUserId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(offset, limit, "", "");
        return userMoneyBuyRepository.findAll(specification, pr);
    }
    
    public UserMoneyBuy get(String id) {
        return userMoneyBuyRepository.findOne(id);
    }
    
    public List<UserMoneyBuy> findByUserId(String userId) {
        List<UserMoneyBuy> list = userMoneyBuyRepository.findByUserId(userId);
        return parse(list);
    }
    
    public List<UserMoneyBuy> parse(List<UserMoneyBuy> list){
        if(!list.isEmpty()){
            for(UserMoneyBuy item : list){
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
