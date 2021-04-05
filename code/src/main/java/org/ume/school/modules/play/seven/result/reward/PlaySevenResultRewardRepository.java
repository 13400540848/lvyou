package org.ume.school.modules.play.seven.result.reward;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.PlaySevenResultReward;

/**
 * 双色球中奖结果详情
 */
public interface PlaySevenResultRewardRepository extends JpaRepository<PlaySevenResultReward, String>, JpaSpecificationExecutor<PlaySevenResultReward> {
	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<PlaySevenResultReward> findByPlayTimeOrderByRewardCodeAsc(Integer playTime);
}
