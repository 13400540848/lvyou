package org.ume.school.modules.school;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

/**
 * Created by Zz on 2021/3/21.
 */
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long>, JpaSpecificationExecutor<SchoolEntity> {
    int countByCode(String code);
    int countByCodeAndIdNot(String code, Long id);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<SchoolEntity> findByName(String name);
}
