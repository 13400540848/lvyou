package com.bluesimon.wbf.modules.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bluesimon.wbf.modules.role.menu.RoleMenuEntity;
import com.bluesimon.wbf.modules.role.menu.RoleMenuService;
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

/**
 * Created by Zz on 2021/3/21.
 */
@Service
public class RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private UserRoleService userRoleService;

    @Transactional
    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<RoleEntity> findAll(RequestPager<RoleEntity> req) {
        final RoleEntity condition = req.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<RoleEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty((condition.getName()))) {
                    predicates.add(cb.like(root.get("name"), "%" + condition.getName() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getCode()))) {
                    predicates.add(cb.like(root.get("code"), "%" + condition.getCode() + "%"));
                }
                if (condition.getStatus()!=null) {
                    predicates.add(cb.equal(root.get("status"), condition.getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(req.getPageIndex(), req.getPageSize(), "", "");
        return roleRepository.findAll(specification, pr);
    }
    
    @Transactional
    public Response<RoleEntity> get(Long id) {
        RoleEntity db = roleRepository.findOne(id);
        if(db==null){
            return new Response<>(Response.NORMAL, "角色不存在");
        }
        return new Response<>(db);
    }

    @Transactional
    public Response<RoleEntity> add(RoleEntity entity) {
        int count = roleRepository.countByName(entity.getName());
        if(count > 0){
            return new Response<>(Response.NORMAL, "名称已存在");
        }
        count = roleRepository.countByCode(entity.getCode());
        if(count > 0){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        roleRepository.saveAndFlush(entity);
        return new Response<>();
    }
    
    @Transactional
    public Response<RoleEntity> edit(RoleEntity entity) {
        RoleEntity db = roleRepository.findOne(entity.getId());
        if(db==null){
            return new Response<>(Response.NORMAL, "角色不存在");
        }
        int count = roleRepository.countByNameAndIdNot(entity.getName(), entity.getId());
        if(count > 0){
            return new Response<>(Response.NORMAL, "名称已存在");
        }
        count = roleRepository.countByCodeAndIdNot(entity.getCode(), entity.getId());
        if(count > 0){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setUpdateTime(new Date());
        roleRepository.saveAndFlush(entity);
        return new Response<>();
    }
    
    @Transactional
    public Response<RoleEntity> delete(Long id) {
        RoleEntity db = roleRepository.findOne(id);
        if(db==null){
            return new Response<>(Response.NORMAL, "角色不存在");
        }
        int count = roleMenuService.countByRoleId(id);
        if(count > 0){
            return new Response<>(Response.NORMAL, "请先删除角色关联的菜单！");
        }
        count = userRoleService.countByRoleId(id);
        if(count > 0){
            return new Response<>(Response.NORMAL, "请先删除角色关联的用户！");
        }

        roleRepository.delete(id);
        return new Response<>();
    }
}
