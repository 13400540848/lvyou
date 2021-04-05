package org.ume.school.modules.project.money.send;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.ProjectMoneySend;

/**
 * Created by Zz on 2018/10/14.
 */
public interface ProjectMoneySendRepository extends JpaRepository<ProjectMoneySend, String>, JpaSpecificationExecutor<ProjectMoneySend> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<ProjectMoneySend> findByProjectIdOrderByCreateTimeDesc(String projectId);
}
