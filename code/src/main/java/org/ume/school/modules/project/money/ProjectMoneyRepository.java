package org.ume.school.modules.project.money;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.ProjectMoney;

/**
 * Created by Zz on 2018/9/1.
 */
public interface ProjectMoneyRepository extends JpaRepository<ProjectMoney, String>, JpaSpecificationExecutor<ProjectMoney> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<ProjectMoney> findByProjectIdAndTypeId(String projectId, String typeId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<ProjectMoney> findByProjectId(String projectId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<ProjectMoney> findByTypeId(String typeId);
}
