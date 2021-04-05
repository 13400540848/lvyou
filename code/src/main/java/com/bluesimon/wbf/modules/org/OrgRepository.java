package com.bluesimon.wbf.modules.org;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Zz on 2021/3/20.
 */
public interface OrgRepository extends JpaRepository<OrgEntity, Long>, JpaSpecificationExecutor<OrgEntity> {

}
