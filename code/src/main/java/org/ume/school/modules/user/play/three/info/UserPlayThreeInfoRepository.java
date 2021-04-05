package org.ume.school.modules.user.play.three.info;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserPlayThreeInfo;

/**
 * 用户投注快3詳情
 */
public interface UserPlayThreeInfoRepository extends JpaRepository<UserPlayThreeInfo, String>, JpaSpecificationExecutor<UserPlayThreeInfo> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlayThreeInfo> findByOrderId(String orderId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlayThreeInfo> findByOrderIdAndStatus(String orderId, Integer status);    
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlayThreeInfo> findByPlayTime(Double playTime);
}
