package com.bluesimon.wbf.modules.role.menu;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zz on 2021/3/21.
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenuEntity, Long>, JpaSpecificationExecutor<RoleMenuEntity> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<RoleMenuEntity> findByRoleId(Long roleId);
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<RoleMenuEntity> findByMenuId(Long menuId);
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<RoleMenuEntity> findByRoleIdAndMenuId(Long roleId, Long menuId);
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<RoleMenuEntity> findByIdIn(List<Long> ids);
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<RoleMenuEntity> findByRoleIdIn(List<Long> roleIds);

    int countByRoleId(Long roleId);
    int countByMenuId(Long menuId);
    
    @Transactional
    Integer deleteByRoleIdAndMenuIdIn(Long roleId, List<Long> menuIds);
    @Transactional
    Integer deleteByMenuIdAndRoleIdIn(Long menuId, List<Long> roleIds);
    @Transactional
    Integer deleteByRoleId(Long roleId);
}
