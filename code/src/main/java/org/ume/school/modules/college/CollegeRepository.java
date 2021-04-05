package org.ume.school.modules.college;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.ume.school.modules.school.SchoolEntity;

/**
 * Created by Zz on 2021/3/21.
 */
public interface CollegeRepository extends JpaRepository<CollegeEntity, Long>, JpaSpecificationExecutor<CollegeEntity> {

}
