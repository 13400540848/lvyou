package com.bluesimon.wbf.modules.menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.enums.YesNoEnum;
import com.bluesimon.wbf.modules.role.menu.RoleMenuEntity;
import com.bluesimon.wbf.modules.role.menu.RoleMenuService;
import com.bluesimon.wbf.modules.user.enums.UserTypeEnum;
import com.bluesimon.wbf.modules.user.role.UserRoleEntity;
import com.bluesimon.wbf.modules.user.role.UserRoleService;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/20.
 */
@Service
public class MenuService {

    @Resource
    private UserRoleService userRoleService;
    
    @Resource
    private RoleMenuService roleMenuService;
    
    @Resource
    private MenuRepository menuRepository;

    @Transactional
    public List<MenuEntity> getByPId(Long pId) {
        return menuRepository.findByPIdOrderBySortAsc(pId);
    }
    
    @Transactional
    public List<MenuEntity> getByType(Integer type) {
        return menuRepository.findByTypeOrderBySortAsc(type);
    }
    
    @Transactional
    public List<MenuEntity> getAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        return menuRepository.findAll(sort);
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<MenuEntity> findAll(RequestPager<MenuEntity> req) {        
        final MenuEntity condition = req.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<MenuEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty((condition.getName()))) {
                    predicates.add(cb.like(root.get("name"), "%" + condition.getName() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getCode()))) {
                    predicates.add(cb.like(root.get("code"), "%" + condition.getCode() + "%"));
                }
                if (condition.getIsHide()!=null) {
                    predicates.add(cb.equal(root.get("isHide"), condition.getIsHide()));
                }
                if (condition.getPId()!=null) {
                    predicates.add(cb.equal(root.get("pId"), condition.getPId()));
                }
                if (condition.getType()!=null) {
                    predicates.add(cb.equal(root.get("type"), condition.getType()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(req.getPageIndex(), req.getPageSize(), "sort", "Asc");
        return menuRepository.findAll(specification, pr);
    }
    
    @Transactional
    public Response<MenuEntity> get(Long id) {
        MenuEntity db = menuRepository.findOne(id);
        if(db==null){
            return new Response<>(Response.NORMAL, "菜单不存在");
        }
        return new Response<>(db);
    }

    @Transactional
    public Response<MenuEntity> add(MenuEntity entity) {
        int count = menuRepository.countByCode(entity.getCode());
        if(count > 0){
            return new Response<>(Response.NORMAL, "菜单编码已存在");
        }
        entity.setOpenMode(entity.getOpenMode()==null?OpenModeEnum.DEFAULT.getValue():entity.getOpenMode());
        entity.setIsHide(entity.getIsHide()==null?YesNoEnum.NO.getValue():entity.getIsHide());
        entity.setPId(entity.getPId()==null?0:entity.getPId());
        entity.setType(entity.getType()==null?MenuTypeEnum.NAV.getValue():entity.getType());
        entity.setSort(entity.getSort()==null?0:entity.getSort());
        entity.setCreateTime(new Date());
        menuRepository.saveAndFlush(entity);
        return new Response<>();
    }
    
    @Transactional
    public Response<MenuEntity> edit(MenuEntity entity) {
        MenuEntity db = menuRepository.findOne(entity.getId());
        if(db==null){
            return new Response<>(Response.NORMAL, "菜单不存在");
        }
        int count = menuRepository.countByCodeAndIdNot(entity.getCode(), entity.getId());
        if(count > 0){
            return new Response<>(Response.NORMAL, "编码已存在");
        }
        menuRepository.saveAndFlush(entity);
        return new Response<>();
    }
    
    @Transactional
    public Response<MenuEntity> delete(Long id) {
        MenuEntity db = menuRepository.findOne(id);
        if(db==null){
            return new Response<>(Response.NORMAL, "菜单不存在");
        }
        int count = roleMenuService.countByMenuId(id);
        if(count > 0){
            return new Response<>(Response.NORMAL, "请先删除关联菜单的角色！");
        }
        menuRepository.delete(id);
        return new Response<>();
    }
    
    @Transactional
    public List<MenuEntity> getByUserId(Long userId, Integer userType) {
        //菜单列表
        List<MenuEntity> ms = new ArrayList<>();
        //超级管理员
        if(userType.equals(UserTypeEnum.ADMIN.getValue())){
            //获取所有菜单
            ms = menuRepository.findByTypeOrderBySortAsc(MenuTypeEnum.MENU.getValue());
            if(StringUtil.isEmpty(ms)){
                return null;
            }
        }else{
            //用户角色
            List<UserRoleEntity> urs = userRoleService.getByUserId(userId);
            if(StringUtil.isEmpty(urs)){
                return null;
            }
            List<Long> roleIds = new ArrayList<>();
            for(UserRoleEntity ur : urs){
                roleIds.add(ur.getRoleId());
            }
            //角色菜单
            List<RoleMenuEntity> rms = roleMenuService.getByRoleIds(roleIds);
            if(StringUtil.isEmpty(rms)){
                return null;
            }
            List<Long> menuIds = new ArrayList<>();
            for(RoleMenuEntity rm : rms){
                menuIds.add(rm.getMenuId());
            }
            //菜单列表
            ms = menuRepository.findByIdInOrderBySortAsc(menuIds);
            if(StringUtil.isEmpty(ms)){
                return null;
            }
        }
        
        //门户列表
        List<Long> pIds = new ArrayList<>();
        for(MenuEntity m : ms){
            pIds.add(m.getPId());
        }
        List<MenuEntity> navs = menuRepository.findByTypeAndIdInOrderBySortAsc(MenuTypeEnum.NAV.getValue(), pIds);
        if(!StringUtil.isEmpty(navs)){
            for(MenuEntity m : navs){
                List<MenuEntity> children = new ArrayList<>();
                for(int i=0;i<ms.size();i++){
                    if(ms.get(i).getPId().equals(m.getId())){
                        children.add(ms.get(i));
                    }
                }
                m.setChildren(children);
            }
        }
        return navs;
    }
}
