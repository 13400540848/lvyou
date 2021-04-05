package org.ume.school.modules.play.three.result.reward;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.PlayThreeResultReward;

/**
 * 中奖结果详情
 */
public interface PlayThreeResultRewardRepository extends JpaRepository<PlayThreeResultReward, String>, JpaSpecificationExecutor<PlayThreeResultReward> {
	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<PlayThreeResultReward> findByPlayTimeOrderByRewardCodeAsc(Double playTime);
}
