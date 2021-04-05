package com.bluesimon.wbf.modules.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bluesimon.wbf.modules.role.menu.RoleMenuEntity;
import com.bluesimon.wbf.modules.user.role.UserRoleEntity;
import com.bluesimon.wbf.modules.user.role.UserRoleService;
import com.bluesimon.wbf.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.modules.role.RoleEntity;
import com.bluesimon.wbf.modules.user.enums.UserStatusEnum;
import com.bluesimon.wbf.modules.user.enums.UserTypeEnum;
import com.bluesimon.wbf.utils.MD5Util;

/**
 * Created by Zz on 2018/8/27.
 */
@Service("userService")
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserRoleService userRoleService;

    public UserEntity getUser(Long userId) {
        UserEntity u = userRepository.findOne(userId);
        return u;
    }

    public int countByAccount(String account){
        return userRepository.countByAccount(account);
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<UserEntity> findAll(RequestPager<UserEntity> pager) {        
        final UserEntity condition = pager.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty((condition.getRealName()))) {
                    predicates.add(cb.like(root.get("realName"), "%" + condition.getRealName() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getNickName()))) {
                    predicates.add(cb.like(root.get("nickName"), "%" + condition.getNickName() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getAccount()))) {
                    predicates.add(cb.like(root.get("account"), "%" + condition.getAccount() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getMobilePhone()))) {
                    predicates.add(cb.like(root.get("mobilePhone"), "%" + condition.getMobilePhone() + "%"));
                }
                if (condition.getType()!=null) {
                    predicates.add(cb.equal(root.get("type"), condition.getType()));
                }
                if (condition.getStatus()!=null) {
                    predicates.add(cb.equal(root.get("status"), condition.getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(pager.getPageIndex(), pager.getPageSize(), "", "");
        return userRepository.findAll(specification, pr);
    }


    public UserEntity login(String account, String password) {
        List<UserEntity> list = userRepository.findByAccountAndPassword(account, password);
        if (null != list && list.size() > 0) {
            UserEntity u = list.get(0);
            return u;
        }
        return null;
    }

    @Transactional
    public Response<UserEntity> saveInfo(UserEntity req, Long userId) {
        UserEntity user = userRepository.findOne(userId);
        if(user==null){
            return new Response<>(Response.NORMAL, "用户不存在");
        }
        user.setMobilePhone(req.getMobilePhone());
        user.setMail(req.getMail());
        user.setUpdateTime(new Date());
        userRepository.saveAndFlush(user);
        return new Response<>();
    }

    @Transactional
    public Response<UserEntity> add(UserEntity entity) {
        int count = userRepository.countByAccount(entity.getAccount());
        if(count > 0){
            return new Response<>(Response.NORMAL, "账号已存在");
        }
        entity.setPassword(MD5Util.getMD5(entity.getPassword()));
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        userRepository.saveAndFlush(entity);
        return new Response<>();
    }
    
    @Transactional
    public Response<UserEntity> edit(UserEntity entity) {
        UserEntity user = userRepository.findOne(entity.getId());
        if(user==null){
            return new Response<>(Response.NORMAL, "用户不存在");
        }
        int count = userRepository.countByAccountAndIdNot(entity.getAccount(), entity.getId());
        if(count > 0){
            return new Response<>(Response.NORMAL, "账号已存在");
        }
        entity.setPassword(user.getPassword());
        entity.setUpdateTime(new Date());
        userRepository.saveAndFlush(entity);
        return new Response<>();
    }
    
    @Transactional
    public UserEntity update(UserEntity user) {
        user.setUpdateTime(new Date());
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public Response<UserEntity> delete(Long id) {
        UserEntity db = userRepository.findOne(id);
        if(db==null){
            return new Response<>(Response.NORMAL, "用户不存在");
        }
        int count = userRoleService.countByUserId(id);
        if(count > 0){
            return new Response<>(Response.NORMAL, "请先删除用户的角色！");
        }
        userRepository.delete(id);
        return new Response<>();
    }
}
