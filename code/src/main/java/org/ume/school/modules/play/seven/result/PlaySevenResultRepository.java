package org.ume.school.modules.play.seven.result;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.PlaySevenResult;

/**
 * 双色球中奖结果
 */
public interface PlaySevenResultRepository extends JpaRepository<PlaySevenResult, String>, JpaSpecificationExecutor<PlaySevenResult> {
	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<PlaySevenResult> findByStatus(Integer status);
	
	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<PlaySevenResult> findByStatusOrderByPlayTimeAsc(Integer status);
}
