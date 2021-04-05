package com.bluesimon.wbf.modules.user.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.bluesimon.wbf.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/21.
 */
@Service
public class UserRoleService {

    @Resource
    private UserRoleRepository userRoleRepository;

    @Transactional
    public List<UserRoleEntity> getByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId);
    }

    @Transactional
    public int countByUserId(Long userId) {
        return userRoleRepository.countByUserId(userId);
    }

    @Transactional
    public int countByRoleId(Long roleId) {
        return userRoleRepository.countByRoleId(roleId);
    }
    
    @Transactional
    public void saveSet(Long userId, List<Long>addIds, List<Long> deleteIds) {
        if(!StringUtil.isEmpty(addIds)){
            this.addBatch(userId, addIds);
        }
        if(!StringUtil.isEmpty(deleteIds)){
            this.deleteByUserIdAndRoleIdIn(userId, deleteIds);
        }
    }
    
    @Transactional
    public Response<List<UserRoleEntity>> addBatch(Long userId, List<Long>addIds) {        
        List<UserRoleEntity> list = new ArrayList<>();
        Date createTime = new Date();
        for(Long id : addIds){
            UserRoleEntity item = new UserRoleEntity();
            item.setUserId(userId);
            item.setRoleId(id);
            item.setCreateTime(createTime);
            list.add(item);
        }
        list = userRoleRepository.save(list);
        return new Response<>(list);
    }
    
    @Transactional
    public void deleteByUserIdAndRoleIdIn(Long roleId, List<Long> menuIds) {
        userRoleRepository.deleteByUserIdAndRoleIdIn(roleId, menuIds);
    }
    
//    /**
//     * 查询并分页
//     */
//    @SuppressWarnings("unchecked")
//    public Page<UserRoleEntity> findAll(RequestPager<UserRoleEntity> req) {
//        final UserRoleEntity condition = req.getCondition();
//        @SuppressWarnings("rawtypes")
//        Specification specification = new Specification<UserRoleEntity>() {
//            @Override
//            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
//                List<Predicate> predicates = new ArrayList<>();
//                if (StringUtils.isNotEmpty((condition.getName()))) {
//                    predicates.add(cb.like(root.get("name"), "%" + condition.getName() + "%"));
//                }
//                if (StringUtils.isNotEmpty((condition.getCode()))) {
//                    predicates.add(cb.like(root.get("code"), "%" + condition.getCode() + "%"));
//                }
//                if (condition.getStatus()!=null) {
//                    predicates.add(cb.equal(root.get("status"), condition.getStatus()));
//                }
//                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
//            }
//        };
//        PageRequest pr = PageUtils.compositePage(req.getPageIndex(), req.getPageSize(), "", "");
//        return userRoleRepository.findAll(specification, pr);
//    }
    
    @Transactional
    public Response<UserRoleEntity> get(Long id) {
        UserRoleEntity menu = userRoleRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "角色不存在");
        }
        return new Response<UserRoleEntity>(menu);
    }

    @Transactional
    public Response<UserRoleEntity> add(UserRoleEntity entity) {
        entity.setCreateTime(new Date());
//        entity.setUpdateTime(new Date());
        entity = userRoleRepository.saveAndFlush(entity);
        return new Response<>(entity);
    }
    
//    @Transactional
//    public Response<UserRoleEntity> edit(UserRoleEntity entity) {
//        UserRoleEntity menu = userRoleRepository.findOne(entity.getId());
//        if(menu!=null){
//            return new Response<>(Response.NORMAL, "角色已存在");
//        }
//        menu = this.getByCodeNotId(entity.getCode(), entity.getId());
//        if(menu!=null){
//            return new Response<>(Response.NORMAL, "编号已存在");
//        }
//        entity.setUpdateTime(new Date());
//        entity = userRoleRepository.saveAndFlush(entity);
//        return new Response<UserRoleEntity>(entity);
//    }
    
//    @Transactional
//    public Response<UserRoleEntity> delete(Long id) {
//        UserRoleEntity menu = userRoleRepository.findOne(id);
//        if(menu==null){
//            return new Response<>(Response.NORMAL, "角色不存在");
//        }
//        userRoleRepository.delete(id);
//        return new Response<UserRoleEntity>();
//    }


}
