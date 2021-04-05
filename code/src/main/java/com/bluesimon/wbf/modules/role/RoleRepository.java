package com.bluesimon.wbf.modules.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Zz on 2021/3/21.
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {
    int countByName(String name);

    int countByNameAndIdNot(String name, Long id);

    int countByCode(String code);

    int countByCodeAndIdNot(String code, Long id);
}
