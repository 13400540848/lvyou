package org.ume.school.modules.user.money.sell;

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
import org.ume.school.modules.model.entity.UserMoneySell;
import org.ume.school.modules.moneytype.MoneyTypeRepository;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.modules.user.UserEntity;
import com.bluesimon.wbf.modules.user.UserRepository;

/**
 * Created by Zz on 2018/9/16.
 */
@Service
public class UserMoneySellService {

    @Resource
    private UserMoneySellRepository userMoneySellRepository;

    @Resource
    private MoneyTypeRepository moneyTypeRepository;

    @Resource
    private UserRepository userRepository;

    @Transactional
    public UserMoneySell submit(UserMoneySell model) {
        return userMoneySellRepository.saveAndFlush(model);
    }

    @Transactional
    public void delete(UserMoneySell model) {
        userMoneySellRepository.delete(model);
    }

    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<UserMoneySell> findAll(final UserMoneySell condition, String offset, String limit) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (condition.getTypeId() != null) {
                    predicates.add(cb.equal(root.get("typeId"), condition.getTypeId()));
                }
                if (condition.getUserId() != null) {
                    predicates.add(cb.equal(root.get("userId"), condition.getUserId()));
                }
                if (condition.getExcludeUserId() != null) {
                    predicates.add(cb.notEqual(root.get("userId"), condition.getExcludeUserId()));
                }
                if (condition.getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), condition.getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(offset, limit, "", "");
        return userMoneySellRepository.findAll(specification, pr);
    }

    public UserMoneySell get(String id) {
        return userMoneySellRepository.findOne(id);
    }

    public List<UserMoneySell> findByUserId(String userId) {
        List<UserMoneySell> list = userMoneySellRepository.findByUserId(userId);
        return parse(list);
    }

    public List<UserMoneySell> parse(List<UserMoneySell> list) {
        if (!list.isEmpty()) {
            for (UserMoneySell item : list) {
                MoneyType t = moneyTypeRepository.findOne(item.getTypeId());
                if (t != null) {
                    item.setTypeName(t.getTypeName());
                }
                t = moneyTypeRepository.findOne(item.getBuyTypeId());
                if (t != null) {
                    item.setBuyTypeName(t.getTypeName());
                }
//                UserEntity u = userRepository.findOne(item.getUserId());
//                if (u != null) {
//                    item.setUserName(u.getUserName());
//                    item.setRealName(u.getRealName());
//                }
            }
        }
        return list;
    }

    public UserMoneySell parse(UserMoneySell model) {
        if (model != null) {
            MoneyType t = moneyTypeRepository.findOne(model.getTypeId());
            if (t != null) {
                model.setTypeName(t.getTypeName());
            }
            t = moneyTypeRepository.findOne(model.getBuyTypeId());
            if (t != null) {
                model.setBuyTypeName(t.getTypeName());
            }
//            UserEntity u = userRepository.findOne(model.getUserId());
//            if (u != null) {
//                model.setUserName(u.getUserName());
//                model.setRealName(u.getRealName());
//            }
        }
        return model;
    }
}
