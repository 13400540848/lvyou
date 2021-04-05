package org.ume.school.modules.project;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.Project;

/**
 * Created by Zz on 2018/8/26.
 */
public interface ProjectRepository extends JpaRepository<Project, String>, JpaSpecificationExecutor<Project> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<Project> findByIsRecommendAndStatus(Integer isRecommend, Integer status);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<Project> findByStatus(Integer status);
    
//    @QueryHints(value = {@QueryHint(name = "name", value = "value")},
//            forCounting = true)
//    public Page<Project> findByTypeAndStatus(String type, Integer status,Pageable pageable);
//
//    @Modifying
//    @Query(value = "update vshop_goods_storehouse set greats=greats+1 where id=?1")
//    public Integer thumbUp(String articleId);
//
//    @Modifying
//    @Query(value = "update vshop_goods_storehouse set commentCnt=commentCnt+1 where id=?1")
//    public Integer commentUp(String articleId);
//
//    List<Project> findByUserId(String userId);
//
//    @QueryHints(value = {@QueryHint(name = "name", value = "value")},
//            forCounting = true)
//    public Page<Project> findByUserId(String userId, Pageable pageable);

    
}
