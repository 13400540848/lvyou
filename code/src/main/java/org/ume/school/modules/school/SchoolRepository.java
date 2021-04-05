package org.ume.school.modules.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Zz on 2021/3/21.
 */
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long>, JpaSpecificationExecutor<SchoolEntity> {

}
