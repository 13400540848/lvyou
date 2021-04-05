package org.ume.school.modules.play.three.result.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.ume.school.modules.model.entity.PlayThreeResultLog;

/**
 * 双色球走势图
 */
public interface PlayThreeResultLogRepository extends JpaRepository<PlayThreeResultLog, String>, JpaSpecificationExecutor<PlayThreeResultLog> {
//	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
//    List<PlayThreeResultLog> findByStatus(Integer status);
}
