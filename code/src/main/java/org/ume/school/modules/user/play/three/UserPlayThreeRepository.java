package org.ume.school.modules.user.play.three;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserPlayThree;

/**
 * 用户投注快3
 */
public interface UserPlayThreeRepository extends JpaRepository<UserPlayThree, String>, JpaSpecificationExecutor<UserPlayThree> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlayThree> findByUserIdOrderByCreateTimeDesc(String userId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlayThree> findByUserIdAndStatus(String userId, Integer status);    
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlayThree> findByPlayTime(Double playTime);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlayThree> findByOrderId(String orderId);
}
