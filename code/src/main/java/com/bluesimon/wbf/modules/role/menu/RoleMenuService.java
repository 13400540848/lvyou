package com.bluesimon.wbf.modules.role.menu;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/21.
 */
@Service
public class RoleMenuService {

    @Resource
    private RoleMenuRepository roleMenuRepository;

    public List<RoleMenuEntity> getByRoleId(Long roleId) {
        return roleMenuRepository.findByRoleId(roleId);
    }

    public int countByRoleId(Long roleId) {
        return roleMenuRepository.countByRoleId(roleId);
    }

    public int countByMenuId(Long menuId) {
        return roleMenuRepository.countByMenuId(menuId);
    }

    public List<RoleMenuEntity> getByMenuId(Long roleId) {
        return roleMenuRepository.findByMenuId(roleId);
    }
    
    public List<RoleMenuEntity> getByRoleIds(List<Long> roleIds) {
        return roleMenuRepository.findByRoleIdIn(roleIds);
    }
    
    public List<RoleMenuEntity> getByIds(List<Long> ids) {
        return roleMenuRepository.findByIdIn(ids);
    }
    
    public List<RoleMenuEntity> getByRoleIdAndMenuId(Long roleId, Long menuId) {
        return roleMenuRepository.findByRoleIdAndMenuId(roleId, menuId);
    }

    @Transactional
    public Response<RoleMenuEntity> add(RoleMenuEntity entity) {
        entity.setCreateTime(new Date());
        entity = roleMenuRepository.saveAndFlush(entity);
        return new Response<>(entity);
    }
    
    @Transactional
    public void saveSetByRole(Long roleId, List<Long>addIds, List<Long> deleteIds) {
        if(!StringUtil.isEmpty(addIds)){
            this.addBatchByRoleId(roleId, addIds);
        }
        if(!StringUtil.isEmpty(deleteIds)){
            this.deleteByRoleIdAndMenuIdIn(roleId, deleteIds);
        }
    }

    @Transactional
    public void saveSetByMenu(Long menuId, List<Long>addIds, List<Long> deleteIds) {
        if(!StringUtil.isEmpty(addIds)){
            this.addBatchByMenuId(menuId, addIds);
        }
        if(!StringUtil.isEmpty(deleteIds)){
            this.deleteByMenuIdAndRoleIdIn(menuId, deleteIds);
        }
    }
    
    @Transactional
    public Response<List<RoleMenuEntity>> addBatchByRoleId(Long roleId, List<Long>addIds) {
        List<RoleMenuEntity> list = new ArrayList<>();
        Date createTime = new Date();
        for(Long id : addIds){
            RoleMenuEntity item = new RoleMenuEntity();
            item.setRoleId(roleId);
            item.setMenuId(id);
            item.setCreateTime(createTime);
            list.add(item);
        }
        roleMenuRepository.save(list);
        return new Response<>();
    }

    @Transactional
    public Response<List<RoleMenuEntity>> addBatchByMenuId(Long menuId, List<Long>addIds) {
        List<RoleMenuEntity> list = new ArrayList<>();
        Date createTime = new Date();
        for(Long id : addIds){
            RoleMenuEntity item = new RoleMenuEntity();
            item.setRoleId(id);
            item.setMenuId(menuId);
            item.setCreateTime(createTime);
            list.add(item);
        }
        roleMenuRepository.save(list);
        return new Response<>();
    }
    
    @Transactional
    public void deleteByRoleId(Long roleId) {
        roleMenuRepository.deleteByRoleId(roleId);
    }
    @Transactional
    public void deleteByRoleIdAndMenuIdIn(Long roleId, List<Long> menuIds) {
        roleMenuRepository.deleteByRoleIdAndMenuIdIn(roleId, menuIds);
    }
    @Transactional
    public void deleteByMenuIdAndRoleIdIn(Long menuId, List<Long> roleIds) {
        roleMenuRepository.deleteByMenuIdAndRoleIdIn(menuId, roleIds);
    }
    @Transactional
    public Response<RoleMenuEntity> delete(Long id) {
        RoleMenuEntity menu = roleMenuRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "角色菜单不存在");
        }
        roleMenuRepository.delete(id);
        return new Response<RoleMenuEntity>();
    }
}
