package org.ume.school.modules.user.play.seven;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserPlaySeven;

/**
 * 用户投注双色球
 */
public interface UserPlaySevenRepository extends JpaRepository<UserPlaySeven, String>, JpaSpecificationExecutor<UserPlaySeven> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlaySeven> findByUserIdOrderByCreateTimeDesc(String userId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlaySeven> findByUserIdAndStatus(String userId, Integer status);    
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlaySeven> findByPlayTime(Double playTime);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlaySeven> findByOrderId(String orderId);
}
