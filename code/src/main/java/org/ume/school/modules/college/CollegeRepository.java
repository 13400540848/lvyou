package org.ume.school.modules.college;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Zz on 2021/3/21.
 */
public interface CollegeRepository extends JpaRepository<CollegeEntity, Long>, JpaSpecificationExecutor<CollegeEntity> {
    int countByCode(String code);
    int countByCodeAndIdNot(String code, Long id);
    int countBySchoolId(Long schoolId);
}
