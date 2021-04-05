package org.ume.school.modules.user.play.seven.info;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserPlaySevenInfo;

/**
 * 用户投注双色球詳情
 */
public interface UserPlaySevenInfoRepository extends JpaRepository<UserPlaySevenInfo, String>, JpaSpecificationExecutor<UserPlaySevenInfo> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlaySevenInfo> findByOrderId(String orderId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlaySevenInfo> findByOrderIdAndStatus(String orderId, Integer status);    
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserPlaySevenInfo> findByPlayTime(Double playTime);
}
