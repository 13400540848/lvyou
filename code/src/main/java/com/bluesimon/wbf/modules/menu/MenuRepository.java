package com.bluesimon.wbf.modules.menu;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

import com.bluesimon.wbf.modules.user.role.UserRoleEntity;

/**
 * Created by Zz on 2021/3/20.
 */
public interface MenuRepository extends JpaRepository<MenuEntity, Long>, JpaSpecificationExecutor<MenuEntity> {

    int countByCode(String code);

    int countByCodeAndIdNot(String code, Long id);

    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<MenuEntity> findByIdInOrderBySortAsc(List<Long> ids);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<MenuEntity> findByTypeAndIdInOrderBySortAsc(Integer Type, List<Long> ids);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<MenuEntity> findByPIdOrderBySortAsc(Long pId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<MenuEntity> findByTypeOrderBySortAsc(Integer Type);
    
}
