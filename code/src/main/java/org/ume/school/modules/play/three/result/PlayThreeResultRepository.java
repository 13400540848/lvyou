package org.ume.school.modules.play.three.result;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.PlayThreeResult;

/**
 * 快3中奖结果
 */
public interface PlayThreeResultRepository extends JpaRepository<PlayThreeResult, String>, JpaSpecificationExecutor<PlayThreeResult> {
	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<PlayThreeResult> findByStatusOrderByPlayTimeAsc(Integer status);
}
