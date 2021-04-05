package com.bluesimon.wbf.modules.user.role;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zz on 2021/3/21.
 */
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long>, JpaSpecificationExecutor<UserRoleEntity> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserRoleEntity> findByUserId(Long userId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserRoleEntity> findByRoleId(Long roleId);

    int countByUserId(Long userId);

    int countByRoleId(Long roleId);
    
    @Transactional
    Integer deleteByUserIdAndRoleIdIn(Long userId, List<Long> roleIds);
}
