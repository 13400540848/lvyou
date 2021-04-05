package org.ume.school.modules.user.money.project;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserMoneyProject;

/**
 * 用户投资项目
 */
public interface UserMoneyProjectRepository extends JpaRepository<UserMoneyProject, String>, JpaSpecificationExecutor<UserMoneyProject> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyProject> findByUserId(String userId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyProject> findByProjectId(String projectId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyProject> findByUserIdAndProjectIdAndStatus(String userId, String projectId, Integer status);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyProject> findByProjectIdAndStatusOrderByCreateTimeDesc(String projectId, Integer status);
    
//    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
//  @Query(value = "update vshop_goods_storehouse set greats=greats+1 where id=?1")
//  public Integer thumbUp(String articleId);
    
//    @Query(value = "select count(distinct user_id) from us_user_money_project where project_id=:projectId", nativeQuery=true)
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    Integer countByProjectId(String projectId);
}
