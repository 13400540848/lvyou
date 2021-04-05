package org.ume.school.modules.play.seven.result.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.ume.school.modules.model.entity.PlaySevenResultLog;

/**
 * 双色球走势图
 */
public interface PlaySevenResultLogRepository extends JpaRepository<PlaySevenResultLog, String>, JpaSpecificationExecutor<PlaySevenResultLog> {
//	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
//    List<PlaySevenResultLog> findByStatus(Integer status);
}
